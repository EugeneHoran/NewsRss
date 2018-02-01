/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eugene.com.livelib;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.util.regex.Pattern;

import okhttp3.HttpUrl;
import retrofit2.Response;

/**
 * Common class used by API responses.
 *
 * @param <T>
 */
@SuppressWarnings("all")
public class ApiResponse<T> {
    private static final Pattern LINK_PATTERN = Pattern.compile("<([^>]*)>[\\s]*;[\\s]*rel=\"([a-zA-Z0-9]+)\"");
    private static final Pattern PAGE_PATTERN = Pattern.compile("\\bpage=(\\d+)");
    private static final String NEXT_LINK = "next";
    public final int code;
    @Nullable
    public final T body;
    @Nullable
    public final String errorMessage;
    @NonNull
    public String nextPage;

    public ApiResponse(Throwable error) {
        code = 500;
        body = null;
        errorMessage = error.getMessage();
    }

    public ApiResponse(Response<T> response) {
        code = response.code();
        if (response.isSuccessful()) {
            body = response.body();
            errorMessage = null;
        } else {
            String message = null;
            if (response.errorBody() != null) {
                try {
                    message = response.errorBody().string();
                } catch (IOException ignored) {
//                    Timber.e(ignored, "error while parsing response");
                }
            }
            if (message == null || message.trim().length() == 0) {
                message = response.message();
            }
            errorMessage = message;
            body = null;
        }
        HttpUrl url = response.raw().request().url();
        if (url != null && url.queryParameterValues("page") != null && url.queryParameterValues("page").size() > 0) {
            nextPage = url.queryParameterValues("page").get(0).toString();
        }
    }

    public boolean isSuccessful() {
        return code >= 200 && code < 300;
    }

    public Integer getNextPage() {
        try {
            return Integer.parseInt(nextPage) + 1;
        } catch (NumberFormatException ex) {
            return null;
        }
    }
}
