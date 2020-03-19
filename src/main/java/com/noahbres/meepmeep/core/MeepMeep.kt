package com.noahbres.meepmeep.core

import com.noahbres.meepmeep.core.colorscheme.ColorManager
import com.noahbres.meepmeep.core.colorscheme.ColorScheme
import com.noahbres.meepmeep.core.entity.AxesEntity
import com.noahbres.meepmeep.core.entity.BotEntity
import com.noahbres.meepmeep.core.entity.Entity
import com.noahbres.meepmeep.core.entity.ThemedEntity
import com.noahbres.meepmeep.core.ui.WindowFrame
import com.noahbres.meepmeep.core.util.LoopManager
import com.noahbres.meepmeep.core.util.FieldUtil
import com.noahbres.meepmeep.core.util.Pose2d
import java.awt.*
import java.awt.event.MouseListener
import java.awt.event.MouseMotionListener
import java.io.File
import javax.imageio.ImageIO
import javax.swing.UIManager

open class MeepMeep<T>(private val windowSize: Int) {
    companion object {
        @JvmStatic
        lateinit var DEFAULT_BOT_ENTITY: BotEntity

        @JvmStatic
        lateinit var DEFAULT_AXES_ENTITY: AxesEntity
    }

    protected val windowFrame = WindowFrame("Meep Meep", windowSize)
    protected val canvas = windowFrame.canvas

    protected var bg: Image? = null

    protected val colorManager = ColorManager()

    protected val entityList = mutableListOf<Entity>()

    // Returns true if entity list needs to be sorted
    private var entityListDirty = false

    protected val fontCMUBoldLight: Font

    private val render: () -> Unit = {
        val g = canvas.bufferStrat.drawGraphics as Graphics2D
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        g.clearRect(0, 0, canvas.width, canvas.height)

        // render
        if (bg != null) {
//            val bgAlpha = 0.6f
//            val resetComposite = g.composite
//            val alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, bgAlpha)
//
//            g.composite = alphaComposite
            g.drawImage(bg, 0, 0, null)
//            g.composite = resetComposite
        }

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

        FieldUtil.CANVAS_WIDTH = windowSize.toDouble()
        FieldUtil.CANVAS_HEIGHT = windowSize.toDouble()

        DEFAULT_BOT_ENTITY = BotEntity(18.0, 18.0, Pose2d(), colorManager.theme, 0.8)
        DEFAULT_AXES_ENTITY = AxesEntity(0.8, colorManager.theme, fontCMUBoldLight, 20f)

        setBackground(Background.GRID_BLUE)

        addEntity(DEFAULT_BOT_ENTITY)
        addEntity(DEFAULT_AXES_ENTITY)
    }

    open fun start(): T {
        windowFrame.isVisible = true

        // Default added entities are initialized before color schemes are set
        // Thus make sure to reset them
        entityList.forEach {
            if (it is ThemedEntity) it.switchScheme(colorManager.theme)
        }

        onCanvasResize()

        Thread(loopManager).start()

        return this as T
    }

    //-------------Theme Settings-------------//
    fun setBackground(background: Background = Background.GRID_BLUE): T {
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

        return this as T
    }

    fun setBackground(image: Image): T {
        bg = image.getScaledInstance(windowSize, windowSize, Image.SCALE_SMOOTH)

        return this as T
    }

    @JvmOverloads
    fun setTheme(schemeLight: ColorScheme, schemeDark: ColorScheme = schemeLight): T {
        colorManager.setTheme(schemeLight, schemeDark)

        entityList.forEach {
            if (it is ThemedEntity) it.switchScheme(colorManager.theme)
        }

        return this as T
    }

    fun setDarkMode(isDarkMode: Boolean): T {
        colorManager.isDarkMode = isDarkMode

        return this as T
    }

    private fun onCanvasResize() {
        FieldUtil.CANVAS_WIDTH = windowSize.toDouble()
        FieldUtil.CANVAS_HEIGHT = windowSize.toDouble()

        entityList.forEach {
            it.setCanvasDimensions(FieldUtil.CANVAS_WIDTH, FieldUtil.CANVAS_HEIGHT)
        }
    }

    //-------------Robot Settings-------------//
    open fun setBotDimensions(width: Double, height: Double): T {
        if (DEFAULT_BOT_ENTITY in entityList) {
            DEFAULT_BOT_ENTITY.setDimensions(width, height)
        }

        return this as T
    }

    //-------------Axes Settings-------------//
    fun setAxesInterval(interval: Int): T {
        if (DEFAULT_AXES_ENTITY in entityList) DEFAULT_AXES_ENTITY.setInterval(interval)

        return this as T
    }

    //-------------Entity-------------//
    fun addEntity(entity: Entity): T {
        entityList.add(entity)
        entityListDirty = true

        if (entity is MouseListener) canvas.addMouseListener(entity)

        if (entity is MouseMotionListener) canvas.addMouseMotionListener(entity)

        return this as T
    }

    fun removeEntity(entity: Entity): T {
        entityList.remove(entity)
        entityListDirty = true

        return this as T
    }

    enum class Background {
        GRID_BLUE,
        FIELD_SKYSTONE,
        FIELD_SKYSTONE_GF,
        FIELD_SKYSTONE_LIGHT,
        FIELD_SKYSTONE_DARK
    }
}