/**
 * Copyright 2016-2019 Bryan Pikaard & Nicholas Sylke
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.typicalbot;

import com.typicalbot.data.serialization.Deserializer;
import com.typicalbot.data.serialization.Serializer;
import com.typicalbot.util.console.ConsoleReader;
import com.typicalbot.data.storage.DataStructure;
import com.typicalbot.shard.Shard;
import com.typicalbot.shard.ShardManager;
import com.typicalbot.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * @author TypicalBot
 * @since 3.0.0-alpha
 */
public class TypicalBot {
    private static final Logger LOGGER = LoggerFactory.getLogger(TypicalBot.class);

    public static final Path HOME_PATH = Paths.get(System.getProperty("user.dir"));
    public static final String VERSION = "@version@";

    public TypicalBot() throws IOException, InterruptedException {
        LOGGER.info("  _____                   _                  _   ____            _   ");
        LOGGER.info(" |_   _|  _   _   _ __   (_)   ___    __ _  | | | __ )    ___   | |_ ");
        LOGGER.info("   | |   | | | | | '_ \\  | |  / __|  / _` | | | |  _ \\   / _ \\  | __|");
        LOGGER.info("   | |   | |_| | | |_) | | | | (__  | (_| | | | | |_) | | (_) | | |_ ");
        LOGGER.info("   |_|    \\__, | | .__/  |_|  \\___|  \\__,_| |_| |____/   \\___/   \\__|");
        LOGGER.info("          |___/  |_|");

        LOGGER.info("");

        LOGGER.info("TypicalBot created by Bryan Pikaard and Nicholas Sylke.");
        LOGGER.info("TypicalBot is licensed under the Apache 2.0 license.");

        LOGGER.info("");

        Arrays.asList("config", "bin", "logs").forEach(directory -> {
            if (!Files.exists(HOME_PATH.resolve(directory))) {
                LOGGER.debug("Directory '{}' does not exist, creating...", directory);
                try {
                    Files.createDirectory(HOME_PATH.resolve(directory));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Arrays.asList("app", "database", "discord", "filter", "sentry").forEach(file -> {
            if (!Files.exists(HOME_PATH.resolve("config/" + file + ".yml"))) {
                LOGGER.debug("File '{}' does not exist, creating...");
                FileUtil.copy(HOME_PATH.resolve("config/" + file + ".yml"), "/config/" + file + ".yml");
            }
        });

        if (!Files.exists(HOME_PATH.resolve("bin/discord.dat"))) {
            ConsoleReader reader = new ConsoleReader();

            LOGGER.info("Please enter the token to register it with the TypicalBot software.");
            String token = reader.readLine().replaceAll("(\\r|\\n|\\t)", "");

            LOGGER.info("Please wait while we retrieve the client identifier.");

            Shard shard = new Shard(token);
            Thread.sleep(5000); // TODO(nsylke): Find the lowest millisecond we need to wait.
            String clientId = shard.getClientId();
            shard.shutdown();

            if (clientId == null) {
                LOGGER.error("The token entered is invalid, please restart the application.");
                System.exit(-1);
            }

            LOGGER.info("Found '" + clientId + "' as the client identifier.");

            Serializer serializer = new Serializer();
            serializer.serialize(String.format("%s:%s", token, clientId), new FileOutputStream(new File(HOME_PATH.resolve("bin/discord.dat").toString())));

            LOGGER.info("Please restart the application.");

            reader.close();
            System.exit(0);
        }

        Deserializer deserializer = new Deserializer();
        // TODO(nsylke): Should move this outside of constructor.
        DataStructure data = new DataStructure();

        LOGGER.info("Starting TypicalBot {}", VERSION);
        /*
          Inside of the data structure should be two values. The first value, or known as '0', should be the
          token of the Discord bot; and the second value, or known as '1', should be the client identifier of
          the Discord bot.
         */
        Arrays.asList(deserializer.deserialize(new FileInputStream(new File(HOME_PATH.resolve("bin/discord.dat").toString()))).toString().split(":")).forEach(data::insert);

        // TODO(nsylke): Start ShardManager - pass the token and client id, along with the shard count.
        ShardManager.register(String.valueOf(data.read(0)), String.valueOf(data.read(0)), 1);
    }

    public static void main(String[] args) {
        try {
            new TypicalBot();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}