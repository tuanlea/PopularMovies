package com.example.tle.popularmovies.main;

import java.io.IOException;

public interface TaskHandler {
    void handleTaskResponse(String json, IOException e);
}
