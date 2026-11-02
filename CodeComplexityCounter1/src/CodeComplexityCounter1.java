import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CodeComplexityCounter1 {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Анализатор сложности кода (Лабораторная №3)");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea();
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(textArea);

        JButton analyzeButton = new JButton("Рассчитать сложность кода");

        frame.add(new JLabel(" Вставьте ваш код ниже:"), BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(analyzeButton, BorderLayout.SOUTH);

        analyzeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedText = textArea.getText();

                if (selectedText == null || selectedText.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Сначала вставьте текст кода!", "Внимание", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                ArrayList<String> stack = new ArrayList<>();
                int maxLoopDepth = 0;
                int currentLoopDepth = 0;

                String[] lines = selectedText.split("\n");

                for (int i = 0; i < lines.length; i++) {
                    String line = lines[i].trim();

                    if (line.contains("for") || line.contains("while")) {
                        currentLoopDepth++;
                        if (currentLoopDepth > maxLoopDepth) {
                            maxLoopDepth = currentLoopDepth;
                        }
                    }

                    if (line.contains("{")) {
                        stack.add("{");
                    }

                    if (line.contains("}") && !stack.isEmpty()) {
                        stack.remove(stack.size() - 1);
                        if (currentLoopDepth > stack.size()) {
                            currentLoopDepth = stack.size();
                        }
                    }
                }

                String bigO;
                if (maxLoopDepth == 0) {
                    bigO = "O(1) — константное время (нет циклов)";
                } else if (maxLoopDepth == 1) {
                    bigO = "O(N) — линейное время";
                } else if (maxLoopDepth == 2) {
                    bigO = "O(N²) — квадратичное время";
                } else if (maxLoopDepth == 3) {
                    bigO = "O(N³) — кубическое время";
                } else {
                    bigO = "O(N^" + maxLoopDepth + ") — высокая полиномиальная сложность";
                }

                JOptionPane.showMessageDialog(frame, "Временная сложность алгоритма: " + bigO, "Результат анализа", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
