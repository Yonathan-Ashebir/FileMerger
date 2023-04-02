package filemerger

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.FileReader
import java.nio.channels.Channels

fun main(args: Array<String>) {
    var path = args[0].trim();
    if (!path.endsWith(File.separatorChar)) path += File.separatorChar;

    val stateReader = FileReader(path + "state.txt")
    val parts = listXDMParts(stateReader)


    var destName: String =
        if (args.size > 1) {
            val destArg = args[1]
            if (destArg.matches(Regex("[:alpha:]:.*|/.*"))) destArg;
            else path + destArg;
        } else path + "xdm.output" //TODO: impelement precence checking

    println("Writing to $destName")

    val output = FileOutputStream(destName)
    val outputChannel = Channels.newChannel(output)

    for (part in parts) {
        val input = FileInputStream(path + part)
        val inputChannel = Channels.newChannel(input)
        ChannelTools.fastChannelCopy(inputChannel, outputChannel)
        inputChannel.close()
        input.close()
        println("Merged part $part ...")
    }
    outputChannel.close()
    output.close()
    println("Completed successfully");
}