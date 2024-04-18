package org.kloud.daos.server;

import org.jetbrains.annotations.NotNull;
import org.kloud.common.ServerConnection;
import org.kloud.daos.BasicDAO;
import org.kloud.model.BaseModel;
import org.kloud.utils.ErrorHandler;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.kloud.utils.Utils.objectToBytes;

public abstract class BasicServerDAO<T extends BaseModel> extends BasicDAO<T> {

    private final ServerConnection serverConnection = new ServerConnection();

    protected abstract String getEndpoint();

    @Override
    protected @NotNull List<T> readObjectsInternal() {
        var readObjects = new ArrayList<T>();
        try {
            HttpResponse<byte[]> response = serverConnection.getRequest(getEndpoint() + "/getall", HttpResponse.BodyHandlers.ofByteArray());
            var bis = new ObjectInputStream(new ByteArrayInputStream(Objects.requireNonNull(response).body()));
            //noinspection unchecked
            return new ArrayList<>((List<T>) bis.readObject()); // TODO: Make sure this is a modifiable list
        } catch (URISyntaxException | IOException | InterruptedException | NullPointerException |
                 ClassNotFoundException e) {
            var res = ErrorHandler.displayException(e).handleWithAction(this::readObjectsInternal);
            return res == null ? readObjects : res;
        }
    }

    @Override
    public boolean removeObject(@NotNull T object) {
        boolean res;
        try {
            HttpResponse<String> response = serverConnection.deleteRequest(getEndpoint() + "/delete/" + object.id, HttpResponse.BodyHandlers.ofString());
            res = response != null && response.statusCode() == 200 && Objects.equals(response.body(), "OK");
        } catch (URISyntaxException | IOException | InterruptedException | NullPointerException e) {
            res = Boolean.TRUE.equals(ErrorHandler.displayException(e).handleWithAction(() -> removeObject(object)));
        }
        if (res) {
            return super.removeObject(object);
        }
        return false;
    }

    @Override
    public boolean addOrUpdateObject(@NotNull T object) {
        boolean res;
        try {
            HttpResponse<String> response = serverConnection.putRequest(getEndpoint() + "/update",
                    HttpRequest.BodyPublishers.ofByteArray(objectToBytes(object)),
                    HttpResponse.BodyHandlers.ofString());
            res = response != null && response.statusCode() == 200 && Objects.equals(response.body(), "OK");
        } catch (URISyntaxException | IOException | InterruptedException | NullPointerException e) {
            res = Boolean.TRUE.equals(ErrorHandler.displayException(e).handleWithAction(() -> addOrUpdateObject(object)));
        }
        if (res) {
            return super.addOrUpdateObject(object);
        }
        return false;
    }

    @Override
    protected @NotNull List<T> readObjects() {
        var objects = super.readObjects();
        for (var obj : objects) {
            obj.postRead();
        }
        return objects;
    }

    @Override
    protected boolean writeObjectsInternal() {
        return true;
    }

    @Override
    public boolean isValid() {
        return serverConnection.isValid();
    }

    @Override
    public void close() {
        // Nothing to close
    }
}
