# Fix Connection Timeout and Discover Screen Crash

The app is experiencing a `SocketTimeoutException` even though the backend is listening on `0.0.0.0:5000`. This usually happens because of host firewall restrictions on the virtual network interface. I will switch to using `localhost:5000` combined with `adb reverse`, which is more reliable for local development.

I will also fix a crash in the `DiscoverScreen` where empty search results could cause a range coercion error.

## Proposed Changes

### Backend Connectivity

#### [MODIFY] [MainActivity.kt](file:///C:/Users/a/AndroidStudioProjects/purrfect/app/src/main/java/com/example/purr_fect/MainActivity.kt)
- Change `PURR_FECT_API_BASE_URL` to `http://localhost:5000`.
- I will run `adb reverse tcp:5000 tcp:5000` to bridge the emulator to your PC's backend. This bypasses host firewall issues by tunneling through ADB.
- Improve error messages to explicitly suggest checking if the backend is running if a connection fails.

### UI / Stability

#### [MODIFY] [MainActivity.kt](file:///C:/Users/a/AndroidStudioProjects/purrfect/app/src/main/java/com/example/purr_fect/MainActivity.kt)
- Fix `DiscoverScreen` crash: Add safety checks when indexing `filteredCats` to prevent `IllegalArgumentException` when the list is empty during transitions.

## Verification Plan

### Automated Tests
- Build the project to ensure no syntax errors.

### Manual Verification
- **ADB Bridge:** I have already executed the `adb reverse` command in the background.
- **Run the App:** Try logging in again. It should now connect via the ADB tunnel to your Node.js server.
- **Search Test:** Go to the Discover screen and try a search that yields no results (e.g., "xyz123"). It should show the "No cats found" state without crashing.
