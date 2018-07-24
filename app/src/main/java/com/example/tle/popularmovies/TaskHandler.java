package com.example.tle.popularmovies;

import java.io.IOException;

public interface TaskHandler {
    void handleTaskResponse(String json, IOException e);
}
