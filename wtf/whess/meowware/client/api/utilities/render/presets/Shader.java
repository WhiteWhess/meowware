package wtf.whess.meowware.client.api.utilities.render.presets;

import wtf.whess.meowware.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.apache.commons.io.IOUtils;
import wtf.whess.meowware.client.api.utilities.Utility;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.lwjgl.opengl.GL20.*;

public final class Shader extends Utility {

    public static Shader TEST = load("default", "test");

    private final int program;

    private final int vertex;
    private final int fragment;

    private Shader(InputStream vertexShader, InputStream fragmentShader) throws Exception {
        program = glCreateProgram();

        vertex = loadShader(vertexShader, GL_VERTEX_SHADER);
        fragment = loadShader(fragmentShader, GL_FRAGMENT_SHADER);

        glAttachShader(program, vertex);
        glAttachShader(program, fragment);

        glLinkProgram(program);

        if(glGetProgrami(program, GL_LINK_STATUS) == 0)
            throw new Exception("Unable to link program");

    }

    public static Shader load(String shader) {
        return load(shader, shader);
    }

    public static Shader load(String vertex, String fragment) {
        try {
            return new Shader(
                    ClassLoader.getSystemClassLoader().getResourceAsStream("assets/minecraft/meowware/shaders/" + vertex + ".vsh"),
                    ClassLoader.getSystemClassLoader().getResourceAsStream("assets/minecraft/meowware/shaders/" + fragment + ".fsh")
            );
        }catch(Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public void use() {
        glUseProgram(program);

        ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());

        glUniform1f(getUniform("texelSize"), resolution.getScaleFactor());
        glUniform2f(getUniform("screenSize"), resolution.getScaledWidth(), resolution.getScaledHeight());
    }

    public void stop() {
        glUseProgram(0);
    }

    public int getUniform(String name) {
        return glGetUniformLocation(program, name);
    }

    private int loadShader(InputStream shaderInput, int type) throws Exception {
        int shader = glCreateShader(type);

        glShaderSource(shader, readFromFile(shaderInput));
        glCompileShader(shader);

        if(glGetShaderi(shader, GL_COMPILE_STATUS) == 0)
            throw new Exception("Unable to compile program");

        return shader;
    }

    private String readFromFile(InputStream shaderInput) throws IOException {
        return new String(IOUtils.toByteArray(shaderInput), StandardCharsets.UTF_8);
    }

}
