package com.example.tle.popularmovies.model;

import java.io.IOException;

public interface TaskHandler {
    void handleTaskResponse(String json, IOException e);
}
