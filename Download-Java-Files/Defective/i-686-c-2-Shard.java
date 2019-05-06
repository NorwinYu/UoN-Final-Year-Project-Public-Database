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
package com.typicalbot.shard;

import com.typicalbot.command.CommandManager;
import com.typicalbot.command.core.ChangelogCommand;
import com.typicalbot.command.core.CommandsCommand;
import com.typicalbot.command.core.DonateCommand;
import com.typicalbot.command.core.HelpCommand;
import com.typicalbot.command.core.InviteCommand;
import com.typicalbot.command.core.PingCommand;
import com.typicalbot.command.core.PrimeCommand;
import com.typicalbot.command.core.ReportCommand;
import com.typicalbot.command.core.ShardsCommand;
import com.typicalbot.command.core.StatisticsCommand;
import com.typicalbot.command.core.SuggestionCommand;
import com.typicalbot.command.core.SupportCommand;
import com.typicalbot.command.core.UptimeCommand;
import com.typicalbot.command.core.VersionCommand;
import com.typicalbot.command.fun.AsciiCommand;
import com.typicalbot.command.fun.BunnyCommand;
import com.typicalbot.command.fun.CatCommand;
import com.typicalbot.command.fun.CookieCommand;
import com.typicalbot.command.fun.DogCommand;
import com.typicalbot.command.fun.EightballCommand;
import com.typicalbot.command.fun.EmojifyCommand;
import com.typicalbot.command.fun.FacesCommand;
import com.typicalbot.command.fun.FlipCommand;
import com.typicalbot.command.fun.HugCommand;
import com.typicalbot.command.fun.JokeCommand;
import com.typicalbot.command.fun.NatoCommand;
import com.typicalbot.command.fun.PunchCommand;
import com.typicalbot.command.fun.QuoteCommand;
import com.typicalbot.command.fun.RandomCommand;
import com.typicalbot.command.fun.ReverseCommand;
import com.typicalbot.command.fun.RockpaperscissorsCommand;
import com.typicalbot.command.fun.RollCommand;
import com.typicalbot.command.fun.RomanCommand;
import com.typicalbot.command.fun.ShootCommand;
import com.typicalbot.command.fun.SlapCommand;
import com.typicalbot.command.fun.StabCommand;
import com.typicalbot.command.fun.ThisorthatCommand;
import com.typicalbot.command.fun.WouldyouratherCommand;
import com.typicalbot.command.fun.YomammaCommand;
import com.typicalbot.command.fun.ZalgolizeCommand;
import com.typicalbot.command.miscellaneous.SayCommand;
import com.typicalbot.command.system.EvalCommand;
import com.typicalbot.command.utility.AvatarCommand;
import com.typicalbot.command.utility.ChannelCommand;
import com.typicalbot.command.utility.MessageCommand;
import com.typicalbot.command.utility.RandomuserCommand;
import com.typicalbot.command.utility.RoleCommand;
import com.typicalbot.command.utility.ServerCommand;
import com.typicalbot.command.utility.UserCommand;
import com.typicalbot.listener.GuildListener;
import com.typicalbot.listener.ReadyListener;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author TypicalBot
 * @since 3.0.0-alpha
 */
// TODO(nsylke): Documentation
public class Shard {
    private static Shard singleton;

    private final String clientId;
    private final int shardId;
    private final int shardTotal;

    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(4);
    private CommandManager commandManager = new CommandManager();
    private JDA instance;

    public Shard(String token) {
        this(token, null, 0, 1);
    }

    public Shard(String token, String clientId, int shardId, int shardTotal) {
        singleton = this;

        this.clientId = clientId;
        this.shardId = shardId;
        this.shardTotal = shardTotal;

        try {
            this.instance = new JDABuilder(AccountType.BOT)
                    .setToken(token)
                    .setAutoReconnect(true)
                    // .setAudioEnabled(true)
                    .setActivity(Activity.playing("Client Started")) // Same as TypicalBot 2.x
                    .setStatus(OnlineStatus.IDLE) // Set to IDLE while still loading, change ONLINE when ready
                    .setBulkDeleteSplittingEnabled(true)
                    .setEnableShutdownHook(true)
                    // .setAudioSendFactory(new NativeAudioSendFactory())
                    .useSharding(shardId, shardTotal)
                    .setCorePoolSize(4)
                    .build();

            this.commandManager.registerCommands(
                    // Core
                    new ChangelogCommand(),
                    new CommandsCommand(),
                    new DonateCommand(),
                    new HelpCommand(),
                    new InviteCommand(),
                    new PingCommand(),
                    new PrimeCommand(),
                    new ReportCommand(),
                    new ShardsCommand(),
                    new StatisticsCommand(),
                    new SuggestionCommand(),
                    new SupportCommand(),
                    new UptimeCommand(),
                    new VersionCommand(),

                    // Fun
                    new AsciiCommand(),
                    new BunnyCommand(),
                    new CatCommand(),
                    new CookieCommand(),
                    new DogCommand(),
                    new EightballCommand(),
                    new EmojifyCommand(),
                    new FacesCommand(),
                    new FlipCommand(),
                    new HugCommand(),
                    new JokeCommand(),
                    new NatoCommand(),
                    new PunchCommand(),
                    new QuoteCommand(),
                    new RandomCommand(),
                    new ReverseCommand(),
                    new RockpaperscissorsCommand(),
                    new RollCommand(),
                    new RomanCommand(),
                    new ShootCommand(),
                    new SlapCommand(),
                    new StabCommand(),
                    new ThisorthatCommand(),
                    new WouldyouratherCommand(),
                    new YomammaCommand(),
                    new ZalgolizeCommand(),

                    // Miscellaneous
                    new SayCommand(),

                    // System
                    new EvalCommand(),

                    // Utility
                    new AvatarCommand(),
                    new ChannelCommand(),
                    new MessageCommand(),
                    new RandomuserCommand(),
                    new RoleCommand(),
                    new ServerCommand(),
                    new UserCommand()
            );

            this.instance.addEventListener(
                    new ReadyListener(),
                    new GuildListener()
            );

            this.executorService.scheduleAtFixedRate(() -> Runtime.getRuntime().gc(), 6, 3, TimeUnit.HOURS);
        } catch (LoginException e) {
            throw new RuntimeException(e);
        }
    }

    public static Shard getSingleton() {
        return singleton;
    }

    /**
     * Get the client identifier, if exists. Otherwise use internal method to get client identifier.
     *
     * @return the client identifier
     */
    public String getClientId() {
        if (this.clientId == null) {
            return Long.toString(this.instance.getSelfUser().getIdLong());
        }

        return this.clientId;
    }

    /**
     * Get the current shard identifier.
     *
     * @return the shard identifier
     */
    public int getShardId() {
        return this.shardId;
    }

    /**
     * Get the total amount of shards.
     *
     * @return total shards
     */
    public int getShardTotal() {
        return this.shardTotal;
    }

    /**
     * Get the JDA singleton.
     *
     * @return JDA singleton
     */
    public JDA getInstance() {
        return this.instance;
    }

    /**
     * Get the current amount of guilds on this shard.
     *
     * @return Guild size
     */
    public int getGuilds() {
        return this.instance.getGuilds().size();
    }

    /**
     * Get the current amount of users on this shard.
     *
     * @return User size
     */
    public int getUsers() {
        return this.instance.getUsers().size();
    }

    /**
     * Get the command manager.
     *
     * @return command manager
     */
    public CommandManager getCommandManager() {
        return this.commandManager;
    }

    /**
     * Get the ping of the Discord API.
     *
     * @return Discord API ping
     */
    public long getPing() {
        return this.instance.getGatewayPing();
    }

    /**
     * Properly shutdown the Discord bot.
     */
    public void shutdown() {
        this.instance.shutdown();
        this.executorService.shutdown();
    }
}
