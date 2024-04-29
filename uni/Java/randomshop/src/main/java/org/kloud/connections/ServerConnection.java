package org.kloud.connections;

import org.jetbrains.annotations.Nullable;
import org.kloud.utils.Conf;
import org.kloud.utils.ErrorHandler;
import org.kloud.utils.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class ServerConnection {

    public boolean isServerOnline() {

        var conf = Conf.getInstance();
        var serverAddress = conf.serverAddress.get();
        var serverAddressParts = serverAddress.split(":");

        if(serverAddressParts.length < 2) {
            ErrorHandler.displayException(new Exception("Server address is in wrong format"));
            return false;
        }

        if(!serverAddress.startsWith("http://")) {
            ErrorHandler.displayException(new Exception("http:// prefix is missing"));
            return false;
        }
        // NOTE: Crappy - crappy url processing, extra horrible, delete this abomination
        var serverHost = (serverAddressParts[0] + ":" + serverAddressParts[1]).replace("http://", "");
        var serverPort = Integer.parseInt(serverAddressParts[2]);

        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(serverHost, serverPort), 5000);
            Logger.success("Server is online");
            return true;
        } catch (IOException e) {
            Logger.success("Server seems offline");
            return Boolean.TRUE.equals(ErrorHandler.displayException(e).handleWithAction(this::isServerOnline));
        }
    }

    private String getEndpointURL(String endpoint) {
        return Conf.getInstance().serverAddress.get() + "/" + endpoint;
    }

    @Nullable
    public <T> HttpResponse<T> getRequest(String endpoint, HttpResponse.BodyHandler<T> bodyHandler) throws URISyntaxException, IOException, InterruptedException {
        if (!isServerOnline()) {
            return null;
        }
        var url = getEndpointURL(endpoint);
        Logger.info("GET " + url);
        //noinspection resource
        return HttpClient.newHttpClient().send(HttpRequest.newBuilder()
                .timeout(Duration.ofSeconds(5))
                .uri(new URI(url))
                .GET().build(), bodyHandler);
    }

    @Nullable
    public <T> HttpResponse<T> putRequest(String endpoint, HttpRequest.BodyPublisher bodyPublisher, HttpResponse.BodyHandler<T> bodyHandler) throws URISyntaxException, IOException, InterruptedException {
        if (!isServerOnline()) {
            return null;
        }
        var url = getEndpointURL(endpoint);
        Logger.info("PUT " + url);
        //noinspection resource
        return HttpClient.newHttpClient().send(HttpRequest.newBuilder()
                .timeout(Duration.ofSeconds(5))
                .uri(new URI(url))
                .PUT(bodyPublisher).build(), bodyHandler);
    }

    @Nullable
    public <T> HttpResponse<T> deleteRequest(String endpoint, HttpResponse.BodyHandler<T> bodyHandler) throws URISyntaxException, IOException, InterruptedException {
        if (!isServerOnline()) {
            return null;
        }
        var url = getEndpointURL(endpoint);
        Logger.info("DELETE " + url);
        //noinspection resource
        return HttpClient.newHttpClient().send(HttpRequest.newBuilder()
                .timeout(Duration.ofSeconds(5))
                .uri(new URI(url))
                .DELETE()
                .build(), bodyHandler);
    }

    public boolean isValid() {
        return isServerOnline();
    }
}
