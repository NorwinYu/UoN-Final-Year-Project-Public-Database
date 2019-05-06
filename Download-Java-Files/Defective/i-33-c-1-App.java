package org.aaroncoplan.waterfall;

import java.util.ArrayList;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.helper.HelpScreenException;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

import org.aaroncoplan.waterfall.parsing.FileParser;
import org.aaroncoplan.waterfall.parsing.ParseResult;

public class App {

    public String getGreeting() {
        return "Hello world.";
    }

    public static void main(String[] args) {
        final Namespace namespace = parseCommandLineArgs(args);

        final Object files = namespace.get("files");
        if (files == null || !(files instanceof ArrayList)) {
            ErrorHandler.exit("[ERROR] Files listed to compile are not a list of strings.");
        }
        @SuppressWarnings("unchecked")
        ArrayList<String> fileList = (ArrayList<String>) files;
        for (String filePath : fileList) {
            final ParseResult parseResult = FileParser.parseFile(filePath);
            System.out.println(filePath);
        }
    }

    private static Namespace parseCommandLineArgs(String[] args) {
        final ArgumentParser argumentParser = ArgumentParsers.newFor("waterfall").build(

        ).defaultHelp(true).description("Waterfall programming language");

        argumentParser.addArgument("files").nargs("+").help("List of files to compile");

        try {
            return argumentParser.parseArgs(args);
        } catch(ArgumentParserException e) {
            if (!(e instanceof HelpScreenException)) {
                System.out.println(e.getMessage());
                argumentParser.printHelp();
            }
            ErrorHandler.exit();
            return null;
        }
    }

}

