package com.noahbres.meepmeep

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.trajectory.Trajectory
import com.noahbres.meepmeep.colorscheme.ColorManager
import com.noahbres.meepmeep.colorscheme.Scheme
import com.noahbres.meepmeep.gfx.DrawCurrentBot
import com.noahbres.meepmeep.ui.WindowFrame
import com.noahbres.meepmeep.util.FieldUtil
import com.noahbres.meepmeep.util.LoopManager
import java.awt.*
import java.io.File
import javax.imageio.ImageIO
import javax.swing.UIManager

class MeepMeep(val windowSize: Int) {
    private val windowFrame = WindowFrame("Meep Meep", windowSize)
    private val canvas = windowFrame.canvas

    private var bg: Image? = null

    private val colorManager = ColorManager()

    private var robotWidth = 18.0
    private var robotHeight = 18.0
    // TODO implement once I make my RR path splitter
//    private var baseConstraints = DriveConstraints(
//            30.0, 30.0, 0.0,
//            Math.toRadians(180.0), Math.toRadians(180.0), 0.0
//    )

    private var trajectoryList = mutableListOf<Trajectory>();

    // Overridable drawing functions
    private val drawDefaultBot = object : DrawCurrentBot {
        override fun draw(gfx: Graphics2D, pose: Pose2d) {

        }
    }

    private val render: () -> Unit = {
        val g = canvas.bufferStrat.drawGraphics as Graphics2D
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        g.clearRect(0, 0, canvas.width, canvas.height)

        // render
        if (bg != null) g.drawImage(bg, 0, 0, null)

        g.dispose()
        canvas.bufferStrat.show()
    }
    private val update: (deltaTime: Long) -> Unit = {
    }

    private val loopManager = LoopManager(60, update, render)

    init {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())

        setBackground(Background.GRID_BLUE)
    }

    fun start(): MeepMeep {
        windowFrame.isVisible = true

        Thread(loopManager).start()

        return this
    }

    //-------------Theme Settings-------------//
    fun setBackground(background: Background = Background.GRID_BLUE): MeepMeep {
        bg = when (background) {
            Background.GRID_BLUE -> {
                colorManager.isDarkMode = false
                ImageIO.read(File("res/background/grid-blue.jpg"))
            }
            Background.FIELD_SKYSTONE -> {
                colorManager.isDarkMode = false
                ImageIO.read(File("res/background/field-skystone.png"))
            }
            Background.FIELD_SKYSTONE_GF -> {
                colorManager.isDarkMode = true
                ImageIO.read(File("res/background/field-skystone-gf.png"))
            }
            Background.FIELD_SKYSTONE_LIGHT -> {
                colorManager.isDarkMode = false
                ImageIO.read(File("res/background/field-skystone-light.jpg"))
            }
            Background.FIELD_SKYSTONE_DARK -> {
                colorManager.isDarkMode = true
                ImageIO.read(File("res/background/field-skystone-dark.jpg"))
            }
        }.getScaledInstance(windowSize, windowSize, Image.SCALE_SMOOTH)

        return this
    }

    fun setBackground(image: Image): MeepMeep {
        bg = image.getScaledInstance(windowSize, windowSize, Image.SCALE_SMOOTH)

        return this
    }

    @JvmOverloads
    fun setTheme(scheme: Scheme, isDarkMode: Boolean = false): MeepMeep {
        colorManager.theme = scheme
        colorManager.isDarkMode = isDarkMode

        return this
    }

    fun setDarkMode(isDarkMode: Boolean): MeepMeep {
        colorManager.isDarkMode = isDarkMode

        return this
    }

    //-------------Robot Settings-------------//
//    fun setBaseConstraints(constraints: DriveConstraints): MeepMeep {
//        baseConstraints = constraints
//
//        return this
//    }

    fun setBotDimensions(width: Double, height: Double): MeepMeep {
        robotWidth = width
        robotHeight = height
        return this
    }

    //-------------Trajectory Settings-------------//
    // TODO implement once I make my RR path splitter
//    fun setStartPoint(point: Pose2d): MeepMeep {
//        return this
//    }

    fun addTrajectory(trajectories: List<Trajectory>): MeepMeep {
        trajectoryList.addAll(trajectories);

        return this
    }

    fun addTrajectory(trajectory: Trajectory): MeepMeep {
        return this
    }

    // TODO implement once I make my RR path splitter
//    fun addTrajectory(trajectoryFeedback: TrajectoryBuilderCallback): MeepMeep {
//        return this
//    }

    fun clearTrajectories(): MeepMeep {
        return this
    }

    enum class Background {
        GRID_BLUE,
        FIELD_SKYSTONE,
        FIELD_SKYSTONE_GF,
        FIELD_SKYSTONE_LIGHT,
        FIELD_SKYSTONE_DARK
    }
}