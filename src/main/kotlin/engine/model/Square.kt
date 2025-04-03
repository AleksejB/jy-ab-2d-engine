import org.example.engine.checkProgramLinkStatus
import org.example.engine.checkShaderCompileStatus
import org.lwjgl.opengl.GL20.*

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

