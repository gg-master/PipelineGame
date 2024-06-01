package PipelineGame.ui;

import PipelineGame.model.Game;
import PipelineGame.model.GameField;
import PipelineGame.model.GameState;
import PipelineGame.model.events.IGameStateListener;
import PipelineGame.model.fillers.RandomizedFieldFiller;
import PipelineGame.model.pipeline.water.properties.Salt;
import PipelineGame.model.pipeline.water.properties.Temperature;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainWindow extends JFrame {
    private Game game;
    private JMenuBar menuBar;
    private final JButton readyButton;

    private final JLabel gameStartWaterLabel;
    private final JLabel gameObjectiveLabel;
    private FieldView fieldView = new FieldView(new GameField(new Dimension(3, 2)));

    private final LinkedHashMap<String, Dimension> menuItemsOfGameModes = new LinkedHashMap<>() {{
        put("Start 2x2 game", new Dimension(2, 2));
        put("Start 3x3 game", new Dimension(3, 3));
        put("Start 4x4 game", new Dimension(4, 4));
        put("Start 5x5 game", new Dimension(5, 5));
        put("Start 15x8 game", new Dimension(15, 8));
        put("Super BIG (30x13) game", new Dimension(30, 13));
    }};

    public MainWindow(){
        setTitle("PipelineGame | Водопроводчик");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.darkGray);
        setResizable(false);

        createMenu();

        Box mainBox = Box.createVerticalBox();
        mainBox.add(Box.createVerticalStrut(10));

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.readyButton = new JButton("Ready");
        this.readyButton.addActionListener(e -> stopGame());
        this.readyButton.setBackground(Color.WHITE);
        this.readyButton.setEnabled(false);
        this.readyButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.gameObjectiveLabel = new JLabel();
        this.gameObjectiveLabel.setForeground(Color.WHITE);
        this.gameObjectiveLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.gameObjectiveLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.gameStartWaterLabel = new JLabel();
        this.gameStartWaterLabel.setForeground(Color.WHITE);
        this.gameStartWaterLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.gameStartWaterLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(this.readyButton);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(this.gameObjectiveLabel);
        centerPanel.add(this.gameStartWaterLabel);
        centerPanel.setBackground(Color.darkGray);

        mainBox.add(centerPanel);
        mainBox.add(Box.createVerticalStrut(10));
        this.fieldView.setEnabledField(false);
        mainBox.add(fieldView);
        setContentPane(mainBox);
        this.pack();
    }

    private void createMenu() {
        this.menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Game");

        for (String menuItemText : this.menuItemsOfGameModes.keySet()) {
            JMenuItem item = new JMenuItem(menuItemText);
            item.addActionListener(new MenuItemListener());
            fileMenu.add(item);
        }
        this.menuBar.add(fileMenu);

        JMenu helpMenu = new JMenu("Справка");
        JMenuItem aboutItem = new JMenuItem("Об игре");
        helpMenu.add(aboutItem);
        this.menuBar.add(helpMenu);

        setJMenuBar(this.menuBar);

        aboutItem.addActionListener(e -> {
            String text = "<html><h2>PipelineGame</h2><h4>Игра \"Водопроводчик\"</h4>" +
                    "Курсовой проект по дисциплине Объектно-ориентированный анализ и программирование<br>" +
                    "Автор: Коршунов Анатолий<br>Дата сборки: 31.05.2024<br></html>";
            JOptionPane.showMessageDialog(MainWindow.this, text,
                    "Об игре", JOptionPane.PLAIN_MESSAGE);
        });
    }

    private void startGame(Dimension gameDimension){
        GameField field = new GameField(gameDimension);
        RandomizedFieldFiller loader = new RandomizedFieldFiller();
        this.game = new Game(field, loader);

        Container mainBox = this.fieldView.getParent();
        mainBox.remove(this.fieldView);

        this.fieldView = new FieldView(field);

        mainBox.add(fieldView);

        this.game.addGameStateListener(new GameStateListener());
        this.game.getPipeline().addPipeLineListener(this.fieldView.pipelineListener);

        this.readyButton.setEnabled(true);

        Temperature temp = (Temperature) game.getExpWater().getPropertyContainer().getProperty(Temperature.class);
        Salt salt = (Salt) game.getExpWater().getPropertyContainer().getProperty(Salt.class);
        this.gameObjectiveLabel.setText("Ожидаемая вода: Teмпература: > " +
                String.format("%.2f", temp.getDegrees()) + " Соли: < " + String.format("%.2f", salt.getPSU()));

        Temperature startTemp = (Temperature) game.getInitWater().getPropertyContainer().getProperty(Temperature.class);
        Salt startSalt = (Salt) game.getInitWater().getPropertyContainer().getProperty(Salt.class);
        this.gameStartWaterLabel.setText("Стартовая вода: " + startTemp.toString() + " " + startSalt.toString());

        this.game.startGame();
        this.pack();
    }

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private void stopGame(){
        this.menuBar.getMenu(0).setEnabled(false);
        this.readyButton.setEnabled(false);
        this.fieldView.setEnabledField(false);

        executor.execute(() -> {
            this.game.stopGame();
            this.menuBar.getMenu(0).setEnabled(true);
        });
    }

    private void showMessageDialog(String str){
        JOptionPane.showMessageDialog(this, str);
    }

    private class MenuItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            startGame(menuItemsOfGameModes.get(command));
        }
    }

    private class GameStateListener implements IGameStateListener {
        @Override
        public void onGameStateChanged(GameState state) {
            if (state == GameState.PlayerWon) {
                showMessageDialog("<html><h2>Вы победили!</h2><i>Вода успешно дотекла до конца трубопровода!</i>");
            } else if (state == GameState.PlayerLost) {
                Temperature temp = (Temperature) game.getExpWater().getPropertyContainer().getProperty(Temperature.class);
                Salt salt = (Salt) game.getExpWater().getPropertyContainer().getProperty(Salt.class);

                showMessageDialog("<html><h2>Вы проиграли!</h2><i>Вода вылилась за пределы трубопровода или <br>не " +
                        "соответствует ожидаемой воде :(</i><br><br>Ожидаемая вода: Teмпература: > " +
                        String.format("%.2f", temp.getDegrees()) + " Соли: < " + String.format("%.2f", salt.getPSU()));
            }
        }
    }
}
