package com.noahbres.meepmeep

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.trajectory.Trajectory
import com.noahbres.meepmeep.colorscheme.ColorManager
import com.noahbres.meepmeep.colorscheme.ColorScheme
import com.noahbres.meepmeep.entity.AxesEntity
import com.noahbres.meepmeep.entity.BotEntity
import com.noahbres.meepmeep.entity.Entity
import com.noahbres.meepmeep.entity.ThemedEntity
import com.noahbres.meepmeep.ui.WindowFrame
import com.noahbres.meepmeep.util.LoopManager
import java.awt.*
import java.awt.event.MouseListener
import java.awt.event.MouseMotionListener
import java.io.File
import javax.imageio.ImageIO
import javax.swing.UIManager

class MeepMeep(val windowSize: Int) {
    companion object {
        @JvmStatic
        lateinit var DEFAULT_BOT_ENTITY: BotEntity
        @JvmStatic
        lateinit var DEFAULT_AXES_ENTITY: AxesEntity
    }

    private val windowFrame = WindowFrame("Meep Meep", windowSize)
    private val canvas = windowFrame.canvas

    private var bg: Image? = null

    private val colorManager = ColorManager()

    private val trajectoryList = mutableListOf<Trajectory>();

    private val entityList = mutableListOf<Entity>()

    // Returns true if entity list needs to be sorted
    private var entityListDirty = false

    private val fontCMUBoldLight: Font

    private val render: () -> Unit = {
        val g = canvas.bufferStrat.drawGraphics as Graphics2D
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        g.clearRect(0, 0, canvas.width, canvas.height)

        // render
        if (bg != null) g.drawImage(bg, 0, 0, null)

        entityList.forEach { it.render(g, canvas.width, canvas.height) }

        // Draw fps
        val fpsFont = Font("Sans", Font.BOLD, 20)
        g.font = fpsFont
        g.color = ColorManager.COLOR_PALETTE.GREEN_600
        g.drawString("${loopManager.fps} FPS", 10, 20)

        g.dispose()
        canvas.bufferStrat.show()
    }
    private val update: (deltaTime: Long) -> Unit = { deltaTime ->

        if (entityListDirty) {
            entityList.sortBy { it.zIndex }
            entityListDirty = false
        }

        entityList.forEach { it.update(deltaTime) }
    }

    private val loopManager = LoopManager(60, update, render)

    init {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())

        fontCMUBoldLight = Font.createFont(Font.TRUETYPE_FONT, File("res/font/cmunbi.ttf")).deriveFont(20f)

        DEFAULT_BOT_ENTITY = BotEntity(18.0, 18.0, Pose2d(), ColorManager.DEFAULT_THEME, 0.8, windowSize, windowSize)
        DEFAULT_AXES_ENTITY = AxesEntity(0.8, ColorManager.DEFAULT_THEME, 0.3, 0.9, windowSize, windowSize, fontCMUBoldLight, 20f)

        setBackground(Background.GRID_BLUE)

        addEntity(DEFAULT_BOT_ENTITY)
        addEntity(DEFAULT_AXES_ENTITY)
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
                ImageIO.read(File("res/background/field-skystone-light-fix.jpg"))
            }
            Background.FIELD_SKYSTONE_DARK -> {
                colorManager.isDarkMode = true
                ImageIO.read(File("res/background/field-skystone-dark-fix.jpg"))
            }
        }.getScaledInstance(windowSize, windowSize, Image.SCALE_SMOOTH)

        return this
    }

    fun setBackground(image: Image): MeepMeep {
        bg = image.getScaledInstance(windowSize, windowSize, Image.SCALE_SMOOTH)

        return this
    }

    @JvmOverloads
    fun setTheme(schemeLight: ColorScheme, schemeDark: ColorScheme = schemeLight): MeepMeep {
        colorManager.setTheme(schemeLight, schemeDark)

        entityList.forEach {
            if (it is ThemedEntity) it.switchScheme(colorManager.theme)
        }

        return this
    }

    fun setDarkMode(isDarkMode: Boolean): MeepMeep {
        colorManager.isDarkMode = isDarkMode

        return this
    }

    //-------------Robot Settings-------------//
    fun setBotDimensions(width: Double, height: Double): MeepMeep {
        if (DEFAULT_BOT_ENTITY in entityList) {
            DEFAULT_BOT_ENTITY.setDimensions(width, height)
        }

        return this
    }

    //-------------Trajectory Settings-------------//
    fun addTrajectory(trajectories: List<Trajectory>): MeepMeep {
        trajectoryList.addAll(trajectories);

        return this
    }

    fun addTrajectory(trajectory: Trajectory): MeepMeep {
        return this
    }

    fun clearTrajectories(): MeepMeep {
        return this
    }

    //-------------Entity-------------//
    fun addEntity(entity: Entity): MeepMeep {
        entityList.add(entity)
        entityListDirty = true

        if (entity is MouseListener) canvas.addMouseListener(entity)

        if (entity is MouseMotionListener) canvas.addMouseMotionListener(entity)

        return this
    }

    fun clearEntity(entity: Entity): MeepMeep {
        entityList.remove(entity)
        entityListDirty = true

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