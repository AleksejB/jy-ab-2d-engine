package org.example

import org.lwjgl.glfw.*
import org.lwjgl.glfw.Callbacks.glfwFreeCallbacks
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL20.*
import org.lwjgl.system.MemoryStack.stackPush
import org.lwjgl.system.MemoryUtil.NULL
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL30.*
import java.nio.FloatBuffer
import java.nio.IntBuffer

class HelloWorld {
    // The window handle
    private var window: Long = 0

    fun run() {
//        System.out.println(("Hello LWJGL " + Version.getVersion()).toString() + "!")

        init()
        loop()

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window)
        glfwDestroyWindow(window)

        // Terminate GLFW and free the error callback
        glfwTerminate()
        glfwSetErrorCallback(null)?.free()
    }

    private fun init() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set()

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        check(glfwInit()) { "Unable to initialize GLFW" }

        // Configure GLFW
        glfwDefaultWindowHints() // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE) // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE) // the window will be resizable
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3)
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3)
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE)
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE)

        // Create the window
        window = glfwCreateWindow(300, 300, "Hello World!", NULL, NULL)
        if (window == NULL) throw RuntimeException("Failed to create the GLFW window")

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(
            window
        ) { window: Long, key: Int, scancode: Int, action: Int, mods: Int ->
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) glfwSetWindowShouldClose(
                window,
                true
            ) // We will detect this in the rendering loop
        }

        stackPush().use { stack ->
            val pWidth = stack.mallocInt(1) // int*
            val pHeight = stack.mallocInt(1) // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight)

            // Get the resolution of the primary monitor
            val vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor())
            val yPos = (vidmode?.height()?.minus(pHeight[0]))?.div(2)
            val xPos = (vidmode?.width()?.minus(pWidth[0]))?.div(2)

            // Center the window
            glfwSetWindowPos(
                window,
                xPos!!,
                yPos!!
            )
        }
        // Make the OpenGL context current
        glfwMakeContextCurrent(window)
        // Enable v-sync
        glfwSwapInterval(1)

        // Make the window visible
        glfwShowWindow(window)
    }

    private fun loop() {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities()

        // Set the clear color
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f)

        val shaderProgram = createShaderProgram(vertexShaderCode, fragmentShaderCode)
        glUseProgram(shaderProgram)

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT) // clear the framebuffer

            render(createQuad(), shaderProgram)

            glfwSwapBuffers(window) // swap the color buffers

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents()
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String> = emptyArray()) {
            HelloWorld().run()
        }
    }
}

fun createQuad(): Int {
    val quadVertices = floatArrayOf(
        -0.5f,  0.5f, // Top-left
        0.5f,  0.5f, // Top-right
        -0.5f, -0.5f, // Bottom-left
        0.5f, -0.5f  // Bottom-right
    )

    val indices = intArrayOf(
        0, 1, 2, // First triangle
        2, 1, 3  // Second triangle
    )

    val vao = glGenVertexArrays()
    glBindVertexArray(vao)

    // Create VBO
    val vbo = glGenBuffers()
    glBindBuffer(GL_ARRAY_BUFFER, vbo)
    val vertexBuffer: FloatBuffer = BufferUtils.createFloatBuffer(quadVertices.size).put(quadVertices).flip()
    glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW)

    // Create EBO (Element Buffer Object for indices)
    val ebo = glGenBuffers()
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo)
    val indexBuffer: IntBuffer = BufferUtils.createIntBuffer(indices.size).put(indices).flip()
    glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexBuffer, GL_STATIC_DRAW)

    // Tell OpenGL how to interpret the data
    glVertexAttribPointer(0, 2, GL_FLOAT, false, 2 * 4, 0)
    glEnableVertexAttribArray(0)

    // Unbind VAO
    glBindVertexArray(0)

    return vao
}

fun render(vao: Int, shaderProgram: Int) {
    glClear(GL_COLOR_BUFFER_BIT)

    glUseProgram(shaderProgram) // Use the shader program
    glBindVertexArray(vao) // Bind the VAO
    glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0) // Draw the quad

    glBindVertexArray(0) // Unbind
}

val vertexShaderCode = """
    #version 330 core
    layout (location = 0) in vec2 aPos;
    out vec2 fragCoord;
    void main() {
        fragCoord = aPos;
        gl_Position = vec4(aPos, 0.0, 1.0);
    }
""".trimIndent()

val fragmentShaderCode = """
    #version 330 core
    in vec2 fragCoord;
    out vec4 FragColor;
    void main() {
        float dist = length(fragCoord);
        if (dist > 0.5) {
            discard;
        }
        FragColor = vec4(1.0, 0.0, 0.0, 1.0);
    }
""".trimIndent()

fun createShaderProgram(vertexSource: String, fragmentSource: String): Int {
    val vertexShader = glCreateShader(GL_VERTEX_SHADER)
    glShaderSource(vertexShader, vertexSource)
    glCompileShader(vertexShader)
    checkShaderCompileStatus(vertexShader)

    val fragmentShader = glCreateShader(GL_FRAGMENT_SHADER)
    glShaderSource(fragmentShader, fragmentSource)
    glCompileShader(fragmentShader)
    checkShaderCompileStatus(fragmentShader)

    val shaderProgram = glCreateProgram()
    glAttachShader(shaderProgram, vertexShader)
    glAttachShader(shaderProgram, fragmentShader)
    glLinkProgram(shaderProgram)
    checkProgramLinkStatus(shaderProgram)

    glDeleteShader(vertexShader)
    glDeleteShader(fragmentShader)

    return shaderProgram
}

fun checkShaderCompileStatus(shader: Int) {
    val status = glGetShaderi(shader, GL_COMPILE_STATUS)
    if (status == GL_FALSE) {
        val log = glGetShaderInfoLog(shader)
        throw RuntimeException("Shader compilation failed: $log")
    }
}

fun checkProgramLinkStatus(program: Int) {
    val status = glGetProgrami(program, GL_LINK_STATUS)
    if (status == GL_FALSE) {
        val log = glGetProgramInfoLog(program)
        throw RuntimeException("Program linking failed: $log")
    }
}

