package de.dfki.mary.voicebuilding

import de.dfki.mary.voicebuilding.tasks.*
import org.gradle.api.Plugin
import org.gradle.api.Project

class MaryttsAudioPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.configurations {
            marytts
        }
        project.repositories {
            jcenter()
            flatDir {
                dirs "$project.buildDir/libs"
            }
        }
        project.dependencies {
            marytts 'de.dfki.mary:marytts-voicebuilding:0.1'
            marytts 'de.dfki.mary:marytts-lang-en:5.2'
            marytts "${project.group}:${project.name}:${project.version}"
        }
        project.task('synthesizeAudioFromText', type: SynthesizeAudioFromText)
        project.task('synthesizeAudioFromRawMaryxml', type: SynthesizeAudioFromRawMaryxml)
    }

}
