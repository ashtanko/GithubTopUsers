package dev.shtanko.topgithub.root

class RootNative {

    external fun checkForRoot(): Int
    external fun checkSuPaths(): Int

    companion object {
        init {
            try {
                System.loadLibrary("root")
            } catch (e: UnsatisfiedLinkError) {
                e.printStackTrace()
            }
        }
    }
}