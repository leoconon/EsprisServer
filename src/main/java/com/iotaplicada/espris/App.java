package com.iotaplicada.espris;

import io.javalin.Javalin;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class App {

    private static final List<Player> players = new ArrayList<>() {{
        add(new Player("noob") {{
            setGaming(true);
            setTotalScore(15232);
            setLastMessage(LocalDateTime.now().minusMinutes(10));
        }});
        add(new Player("pro_player") {{
            setGaming(true);
            setTotalScore(200);
            setLastMessage(LocalDateTime.now().plusMinutes(10));
        }});
    }};

    public static void main(String... args) {
        Javalin app = Javalin.create(conf -> {
            conf.enableCorsForAllOrigins();
            conf.addStaticFiles("/public");
        });

        app.get("/gaming/:user/:score", ctx -> {
            var name = ctx.pathParam("user");
            var score = Integer.parseInt(ctx.pathParam("score"));
            var player = new Player(name);
            if (players.contains(player)) {
                player = players.get(players.indexOf(player));
            } else {
                players.add(player);
            }
            player.setActualScore(score);
            player.setTotalScore(player.getTotalScore() + player.getActualScore());
            player.setGaming(true);
            player.setLastMessage(LocalDateTime.now());
            players.forEach(System.out::println);
        });

        app.get("/status/", ctx -> {
            players.sort((p1, p2) -> p2.getTotalScore() - p1.getTotalScore());
            ctx.json(players);
        });

        app.start(getPort());
    }

    private static int getPort() {
        String port = System.getenv("PORT");
        if (port == null) {
            return 7000;
        }
        return Integer.parseInt(port);
    }

}
