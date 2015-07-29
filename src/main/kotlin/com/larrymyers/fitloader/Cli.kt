package com.larrymyers.fitloader

import net.sourceforge.argparse4j.ArgumentParsers
import net.sourceforge.argparse4j.inf.ArgumentParserException
import net.sourceforge.argparse4j.inf.Namespace

fun main(args: Array<String>) {
    val parser = ArgumentParsers.newArgumentParser("LoadFitFile")
            .defaultHelp(true)
            .description("Load a .fit file from disk.")

    parser.addArgument("file").nargs("*")
            .help("path to fit file")


    val ns: Namespace

    try {
        ns = parser.parseArgs(args)
    } catch (ex: ArgumentParserException) {
        parser.handleError(ex)
        return System.exit(1)
    }

    for (filename in ns.getList<String>("file")) {
        println(filename)
    }
}
