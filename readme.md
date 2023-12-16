# fullstack-web-editor

## About
The frontend uses the Monaco editor, and Kotlin/WASM with Ktor client.
The backend is made in Kotlin and uses the Ktor server.

## Starting the app
```
# Start the backend
./gradlew jvmRun -DmainClass=ApplicationKt

# Start the frontend
./gradlew wasmJsBrowserRun
```

Now you can visit [localhost](http://localhost:8080/) and use the editor.


