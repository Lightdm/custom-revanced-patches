rootProject.name = "custom-revanced-patches"

buildCache {
    local {
        isEnabled = "CI" !in System.getenv()
    }
}
