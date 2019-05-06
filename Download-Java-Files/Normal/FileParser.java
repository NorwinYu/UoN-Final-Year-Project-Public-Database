package org.aaroncoplan.waterfall.parsing;

import com.aaroncoplan.waterfall.WaterfallLexer;
import com.aaroncoplan.waterfall.WaterfallParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import org.aaroncoplan.waterfall.ErrorHandler;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class FileParser {

    public static ParseResult parseFile(final String filePath) {
        final StringBuilder codeString = new StringBuilder();

        final File codeFile = new File(filePath);
        if (!codeFile.exists()) {
            ErrorHandler.exit("File '%s' does not exist", filePath);
            return null;
        }
        if (!codeFile.canRead()) {
            ErrorHandler.exit("File '%s' cannot be read", filePath);
            return null;
        }
        if (!codeFile.isFile()) {
            ErrorHandler.exit("File '%s' is not a file", filePath);
            return null;
        }
        try {
            Scanner scanner = new Scanner(codeFile);
            while (scanner.hasNextLine()) {
                codeString.append(scanner.nextLine() + "\n");
            }
            scanner.close();
        } catch(FileNotFoundException e) {
            ErrorHandler.exit("File '%s' does not exist", filePath);
            return null;
        }
        final CharStream charStream = CharStreams.fromString(codeString.toString());
        final WaterfallLexer waterfallLexer = new WaterfallLexer(charStream);
        final CommonTokenStream tokenStream = new CommonTokenStream(waterfallLexer);

        final WaterfallParser waterfallParser = new WaterfallParser(tokenStream);
        waterfallParser.removeErrorListeners();
        final SyntaxErrorListener errorListener = new SyntaxErrorListener(filePath);
        waterfallParser.addErrorListener(errorListener);

        final WaterfallParser.ProgramContext programAST = waterfallParser.program();
        final List<String> syntaxErrors = errorListener.getSyntaxErrors();
        return new ParseResult(filePath, syntaxErrors, programAST);
    }

}

