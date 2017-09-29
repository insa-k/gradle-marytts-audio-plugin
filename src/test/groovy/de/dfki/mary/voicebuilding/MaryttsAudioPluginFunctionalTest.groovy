package de.dfki.mary.voicebuilding

import org.gradle.testkit.runner.GradleRunner
import org.testng.annotations.*

import static org.gradle.testkit.runner.TaskOutcome.SUCCESS

class MaryttsAudioPluginFunctionalTest {

    GradleRunner gradle

    @BeforeSuite
    void setup() {
        def projectDir = File.createTempDir()
        gradle = GradleRunner.create().withProjectDir(projectDir).withPluginClasspath()
        new File(projectDir, 'build.gradle').withWriter {
            it << this.class.getResourceAsStream('build.gradle')
        }
        new File(projectDir, 'settings.gradle').withWriter {
            it << this.class.getResourceAsStream('settings.gradle')
        }
    }

    @Test
    void canApplyPlugin() {
        def result = gradle.withArguments().build()
        assert true
    }

    @Test
    void canBuildVoice() {
        def result = gradle.withArguments('legacyInit').build()
        println result.output
        def result2 = gradle.withArguments('build').build()
        println result2.output
    }

    @Test
    void canSynthesizeAudioFromText() {
        def result = gradle.withArguments('synthesizeAudioFromText').build()
        println result.output
    }

    @Test
    void canSynthesizeAudioFromRawMaryxml() {
        def result = gradle.withArguments('synthesizeAudioFromRawMaryxml').build()
        println result.output
    }
}
