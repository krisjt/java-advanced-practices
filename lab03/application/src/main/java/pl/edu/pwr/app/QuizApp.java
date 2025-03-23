package pl.edu.pwr.app;

import pl.edu.pwr.apiconnetion.models.Question;
import pl.edu.pwr.service.GuessingQuestion;
import pl.edu.pwr.service.Language;

import javax.swing.*;
import java.awt.*;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;

public class QuizApp extends JFrame {
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private JTextField questionCountField;
    private JLabel questionLabel;
    private JCheckBox[] answerCheckBoxes;
    private JButton nextButton;
    private JLabel summaryLabel;
    private JPanel answersPanel;
    private Question currentQuestion;
    private GuessingQuestion game;
    private Random random = new Random();
    private ResourceBundle rb;
    private Locale locale;
    private JRadioButton plRadioButton;
    private JRadioButton enRadioButton;

    private int currentQuestionIndex;
    private int totalQuestions;
    private int score;
    private Language language = Language.PL;

    public QuizApp() {
        setTitle("Quiz App");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        initializeLanguage();

        createStartPage();
        createQuestionPage();
        createSummaryPage();

        add(cardPanel);
        cardLayout.show(cardPanel, rb.getString("start"));
    }

    private void initializeLanguage() {
        game = new GuessingQuestion(language);
        locale = new Locale(language.toString().toLowerCase(), language.toString());
        rb = ResourceBundle.getBundle("App", locale);
//        rb = ResourceBundle.getBundle("pl.edu.pwr.app.resource.App", locale);
    }

    private void updateLanguage(Language newLanguage) {
        if (this.language != newLanguage) {
            this.language = newLanguage;
            initializeLanguage();

            cardPanel.removeAll();
            createStartPage();
            createQuestionPage();
            createSummaryPage();

            cardLayout.show(cardPanel, rb.getString("start"));
            revalidate();
            repaint();
        }
    }

    private void createStartPage() {
        JPanel startPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.BOTH;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel languageLabel = new JLabel(rb.getString("language.select"), SwingConstants.CENTER);
        languageLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
        startPanel.add(languageLabel, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        plRadioButton = new JRadioButton("Polski");
        enRadioButton = new JRadioButton("English");

        if (language == Language.PL) {
            plRadioButton.setSelected(true);
        } else {
            enRadioButton.setSelected(true);
        }

        ButtonGroup languageGroup = new ButtonGroup();
        languageGroup.add(plRadioButton);
        languageGroup.add(enRadioButton);

        plRadioButton.addActionListener(e -> updateLanguage(Language.PL));
        enRadioButton.addActionListener(e -> updateLanguage(Language.EN));

        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        radioPanel.add(plRadioButton);
        radioPanel.add(enRadioButton);
        gbc.gridwidth = 2;
        startPanel.add(radioPanel, gbc);

        gbc.gridy = 2;
        JLabel titleLabel = new JLabel(rb.getString("welcome"), SwingConstants.CENTER);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
        startPanel.add(titleLabel, gbc);

        gbc.gridy = 3;
        JLabel instructionLabel = new JLabel(rb.getString("question.number"), SwingConstants.CENTER);
        instructionLabel.setFont(new Font("Times New Roman", Font.PLAIN, 24));
        startPanel.add(instructionLabel, gbc);

        gbc.gridy = 4;
        questionCountField = new JTextField();
        startPanel.add(questionCountField, gbc);

        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.weighty = 0;
        JButton startButton = new JButton(rb.getString("start"));
        startButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        startPanel.add(startButton, gbc);

        startButton.addActionListener(e -> {
            try {
                totalQuestions = Integer.parseInt(questionCountField.getText());
                if (totalQuestions > 0) {
                    score = 0;
                    currentQuestionIndex = 0;
                    createQuestionPage();
                    loadNextQuestion();
                    cardLayout.show(cardPanel, rb.getString("question"));
                } else {
                    JOptionPane.showMessageDialog(QuizApp.this, rb.getString("error.number.of.questions"));
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(QuizApp.this, rb.getString("error.valid.number"));
            }
        });

        cardPanel.add(startPanel, rb.getString("start"));
    }

    private void createQuestionPage() {
        JPanel questionPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;

        questionLabel = new JLabel("", SwingConstants.CENTER);
        questionLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));

        JScrollPane questionScrollPane = new JScrollPane(questionLabel);
        questionScrollPane.setPreferredSize(new Dimension(700, 100));
        questionScrollPane.setBorder(BorderFactory.createEmptyBorder());
        questionScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        gbc.weighty = 0.3;
        questionPanel.add(questionScrollPane, gbc);

        gbc.gridy++;
        gbc.weighty = 0.6;
        answersPanel = new JPanel();
        answersPanel.setLayout(new BoxLayout(answersPanel, BoxLayout.Y_AXIS));
        answersPanel.setOpaque(true);

        JScrollPane scrollPane = new JScrollPane(answersPanel);
        scrollPane.setPreferredSize(new Dimension(700, 300));
        scrollPane.setMinimumSize(new Dimension(700, 300));
        scrollPane.setMaximumSize(new Dimension(Short.MAX_VALUE, 300));
        questionPanel.add(scrollPane, gbc);

        gbc.gridy++;
        gbc.weighty = 0.1;
        nextButton = new JButton(rb.getString("next"));
        nextButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        nextButton.addActionListener(e -> {
            checkAnswer();

            currentQuestionIndex++;
            if (currentQuestionIndex < totalQuestions) {
                Timer timer = new Timer(1000, event -> {
                    answersPanel.setBackground(null);
                    answersPanel.repaint();
                    loadNextQuestion();
                });
                timer.setRepeats(false);
                timer.start();
            } else {
                Timer timer = new Timer(1000, event -> {
                    answersPanel.setBackground(null);
                    answersPanel.repaint();
                    showSummary();
                });
                timer.setRepeats(false);
                timer.start();
            }
        });
        questionPanel.add(nextButton, gbc);

        cardPanel.add(questionPanel, rb.getString("question"));
    }

    private void createSummaryPage() {
        JPanel summaryPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;

        summaryLabel = new JLabel("", SwingConstants.CENTER);
        JButton restartButton = new JButton(rb.getString("restart"));

        restartButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        restartButton.addActionListener(e -> cardLayout.show(cardPanel, rb.getString("start")));

        gbc.gridy++;
        summaryLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        summaryPanel.add(summaryLabel, gbc);
        gbc.gridy++;
        summaryPanel.add(restartButton, gbc);

        cardPanel.add(summaryPanel, rb.getString("summary"));
    }

    private void loadNextQuestion() {
        int question = Math.abs(random.nextInt() % 4);
//        Map<String,Boolean> map = new HashMap<>();
//        map.put("true1",true);
//        map.put("true2",true);
//        map.put("false1",false);
//        map.put("false2",false);
        if (currentQuestionIndex == 0) currentQuestion = game.getNumberQuestion();
        else {
            switch (question) {
//                case 0 -> currentQuestion = game.getQuestion(Subject.Author);
//                case 1 -> currentQuestion = game.getQuestion(Subject.Kind);
//                case 2 -> currentQuestion = game.getQuestion(Subject.Genre);
                default -> currentQuestion = game.getQuestionThreeGaps();
//                default -> currentQuestion = new Question("cos",map);
            }
        }

        System.out.println(currentQuestion);

        String questionText = currentQuestion.questionContent();
        questionLabel.setText("<html><div style='width: 650px; text-align: center;'>" + questionText + "</div></html>");

        answersPanel.removeAll();

        int answerCount = currentQuestion.answers().size();
        answerCheckBoxes = new JCheckBox[answerCount];

        int i = 0;
        for (Map.Entry<String, Boolean> entry : currentQuestion.answers().entrySet()) {
            answerCheckBoxes[i] = new JCheckBox(entry.getKey());
            answerCheckBoxes[i].setFont(new Font("Times New Roman", Font.PLAIN, 20));
            answerCheckBoxes[i].setSelected(false);
            answersPanel.add(answerCheckBoxes[i]);
            i++;
        }

        answersPanel.revalidate();
        answersPanel.repaint();

    }

    private void checkAnswer() {
        boolean hasCorrectAnswer = true;

        for (JCheckBox answerCheckBox : answerCheckBoxes) {
            if ((answerCheckBox.isSelected() && Boolean.FALSE.equals(currentQuestion.answers().get(answerCheckBox.getText())))
            || (!answerCheckBox.isSelected() && Boolean.TRUE.equals(currentQuestion.answers().get(answerCheckBox.getText())))){
                hasCorrectAnswer = false;
                break;
            }
        }

        if (hasCorrectAnswer) {
            answersPanel.setBackground(Color.GREEN);
            score++;
        } else {
            answersPanel.setBackground(Color.RED);
        }

        answersPanel.repaint();
    }

    private void showSummary() {
        Object[] numbers = {score,totalQuestions};
        MessageFormat mf = new MessageFormat("");
        mf.setLocale(locale);
        mf.applyPattern(rb.getString("result"));

        summaryLabel.setText(mf.format(numbers));
        cardLayout.show(cardPanel, rb.getString("summary"));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new QuizApp().setVisible(true));
    }
}