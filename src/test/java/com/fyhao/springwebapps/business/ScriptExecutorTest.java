package com.fyhao.springwebapps.business;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;

import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ScriptExecutorTest {

    @TempDir
    Path temporaryDirectory;

    @Test
    void readsAndWritesAFile() throws Exception {
        Path file = temporaryDirectory.resolve("nashorn.txt");
        String script = "var Files = Java.type('java.nio.file.Files');"
                + "var Paths = Java.type('java.nio.file.Paths');"
                + "var Charsets = Java.type('java.nio.charset.StandardCharsets');"
                + "var path = Paths.get(filePath);"
                + "Files.write(path, new java.lang.String('written by Nashorn').getBytes(Charsets.UTF_8));"
                + "new java.lang.String(Files.readAllBytes(path), Charsets.UTF_8);";

        Object result = ScriptExecutor.evaluate(script, Collections.singletonMap("filePath", file.toString()));

        assertThat(result).isEqualTo("written by Nashorn");
        assertThat(Files.readString(file)).isEqualTo("written by Nashorn");
    }

    @Test
    void callsHttpEndpoint() throws Exception {
        HttpServer server = startHttpServer();
        try {
            String endpoint = "http://127.0.0.1:" + server.getAddress().getPort() + "/message";
            String script = "var Scanner = Java.type('java.util.Scanner');"
                    + "var stream = new java.net.URL(endpoint).openStream();"
                    + "var response;"
                    + "try { response = new Scanner(stream, 'UTF-8').useDelimiter('\\\\A').next(); }"
                    + "finally { stream.close(); }"
                    + "response;";

            Object result = ScriptExecutor.evaluate(script, Collections.singletonMap("endpoint", endpoint));

            assertThat(result).isEqualTo("hello from HTTP");
        } finally {
            server.stop(0);
        }
    }

    @Test
    void accessesBuiltInAndCustomJavaTypes() throws Exception {
        String script = "var ArrayList = Java.type('java.util.ArrayList');"
                + "var CustomService = Java.type('com.fyhao.springwebapps.business.CustomService');"
                + "var values = new ArrayList(); values.add('java');"
                + "values.get(0) + ':' + new CustomService().add(20, 22);";

        assertThat(ScriptExecutor.evaluate(script)).isEqualTo("java:42");
    }

    @Test
    void executesScriptFromClasspathResource() {
        String result = ScriptExecutor.runcodeResource("test1.js");
        String[] values = result.split("\\|");

        assertThat(values).hasSize(2);
        assertThat(values[0]).isEqualTo(values[1]);
    }

    private HttpServer startHttpServer() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        server.createContext("/message", exchange -> {
            byte[] body = "hello from HTTP".getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(200, body.length);
            exchange.getResponseBody().write(body);
            exchange.close();
        });
        server.start();
        return server;
    }
}
