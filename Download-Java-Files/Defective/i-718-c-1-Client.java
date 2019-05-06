import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import javafx.concurrent.Task;

import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

public class Client {

    private static final String DATA_CHAT_ROOM = "data_chat_room";
    private static final String DATA_SCOREBOARD = "data_scoreboard";
    private static final String DATA_ITEM_COST = "data_item_cost";
    private static final String UPDATE_SCOREBOARD = "update_scoreboard";
    private static final String ADD_MESSAGE_TO_CHAT_ROOM = "add_message_to_chat_room";
    private static final String INIT_SCOREBOARD = "init_scoreboard";
    private static final String INIT_CHAT_ROOM = "init_chat_room";
    private static final String GET_ITEM_COST = "get_item_cost";
    private static final String BUY_ITEM = "buy_item";
    private static final String BOUGHT_ITEM = "bought_item";
    private static final String end = "#";

    View view;
    Socket socket;
    Scanner scanner;
    Formatter formatter;

    Chatroom chatroom ;
    Scoreboard scoreboard ;
    MultiPlayerMenu multiPlayerMenu ;

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public MultiPlayerMenu getMultiPlayerMenu() {
        return multiPlayerMenu;
    }

    public Chatroom getChatRoom() {
        return chatroom;
    }

    Client(View view){
        this.view = view;
        chatroom = new Chatroom(view);
        multiPlayerMenu = new MultiPlayerMenu(view);
        scoreboard = new Scoreboard(view);
    }

    private String getData(Scanner scanner) {
        StringBuilder s = new StringBuilder();
        while(true) {
            String line = scanner.nextLine();
            if(line.equals(end)) break;
            s.append(line + "\n");
        }
        return s.toString();
    }

    Task<Void> read = new Task<Void>() {
        @Override
        protected Void call() throws Exception {

            while (socket.isConnected()) {
                String command = scanner.nextLine();
                process(command, getData(scanner));
            }
            return null;
        }
    };



    void initialize() {
        try {
            socket = new Socket("localhost", 8050);
            scanner = new Scanner(socket.getInputStream());
            formatter = new Formatter(socket.getOutputStream());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    boolean checkId(String id) throws IOException {
        System.err.println(socket.isConnected());
        System.err.println("id is :" + id);
        formatter.format(id + "\n");
        formatter.flush();
        String result = scanner.nextLine();
        System.err.println("result is :" + result);
        if (result.equals("userName inValid")) {
            scanner.close();
            formatter.close();
            return false;
        } else {
            int port = scanner.nextInt();
            System.err.println("port is " + port);
            formatter.format("Connected with port : " + port + '\n');
            formatter.flush();
            formatter.close();
            scanner.close();
            while(true ){
                try {
                    socket = new Socket("localhost", port);
                    break ;
                } catch(Exception e) {}
            }
            scanner = new Scanner(socket.getInputStream());
            formatter = new Formatter(socket.getOutputStream());
            return true;
        }

    }

    public void run() {
        new Thread(read).start();
        initChatRoom();
        initScoreboard();
    }

    //talk to server.
    synchronized void command(String command) {
        formatter.format(command);
        formatter.flush();
    }

    //decoding what's server saying.
    private void process(String command, String text) {
        Scanner reader;
        String item, price;

        switch (command) {
            case DATA_CHAT_ROOM: {
                Gson gson = new Gson();
                Talk[] talks = gson.fromJson(text, Talk[].class);
                //chatroom.setContent(talks);
                chatroom.setContent(text);
                break;
            }
            case DATA_SCOREBOARD: {
                Gson gson = new Gson();
                Person[] people = gson.fromJson(text, Person[].class);
                //scoreboard.setContent(people);
                scoreboard.setContent(text);
                break;
            }
            case DATA_ITEM_COST:
                reader = new Scanner(text);
                item = reader.nextLine();
                price = reader.nextLine();
                //todo.
                break;
            case BOUGHT_ITEM :
                reader = new Scanner(text);
                item = reader.nextLine();
                //todo
        }
    }

    public void addMessageToChatRoom(String text) {
        String command = ADD_MESSAGE_TO_CHAT_ROOM + "\n" + text + '\n' + end + "\n";
        command(command);
    }

    public void updateScoreboard(String level) {
        String command = UPDATE_SCOREBOARD + "\n" + level + '\n' + end + "\n";
        command(command);
    }

    public void initScoreboard() {
        String command = INIT_SCOREBOARD + "\n" + end + "\n";
        command(command);
    }

    public void initChatRoom() {
        String command = INIT_CHAT_ROOM + "\n" + end + "\n";
        command(command);
    }

    public void getItemCost(String item) {
        String command = GET_ITEM_COST + "\n" + item + "\n" + end + "\n";
        command(command);
    }

    public void buyItem(String item) {
        String command = BUY_ITEM + "\n" + item + "\n" + end + "\n";
        command(command);
    }
}
