package de.dfki.mary.voicebuilding.tasks

import groovy.json.JsonBuilder
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.*
import marytts.*
import org.apache.commons.io.FileUtils

class SynthesizeAudioFromText extends DefaultTask {

    @InputDirectory
    File srcDir = project.file("$project.buildDir/input/text")

    @OutputDirectory
    File destDir = project.file("$project.buildDir/output/text/wav")

    @Input
    Map<String, String> maryttsProperties = ['mary.base': "$project.buildDir/resources/legacy"]

    @TaskAction
    void synthesize() {
        FileUtils.cleanDirectory(destDir)
        def batch = []
        project.fileTree(srcDir).include("*.txt").each { txtFile ->
            def wavFile = project.file("$destDir/${txtFile.name - ".txt" + ".wav"}")

            batch << [
                    locale    : "en_US",
                    inputType : "TEXT",
                    outputType: "AUDIO",
                    inputFile : "$txtFile",
                    outputFile: "$wavFile"
            ]
        }
        def batchFile = project.file("$temporaryDir/batch.json")
        if(batchFile.exists()) {
            batchFile.delete()
        }
        batchFile.createNewFile()

        batchFile.text = new JsonBuilder(batch).toPrettyString()

        project.javaexec {
            classpath project.configurations.marytts
            main 'marytts.BatchProcessor'
            args batchFile
            systemProperties << maryttsProperties
        }

    }

}