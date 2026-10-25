import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BMICalculator extends JFrame implements ActionListener, MouseListener {

    private final Color TEAL = new Color(22, 160, 133);
    private final Color ORANGE = new Color(230, 126, 34);
    private final Color GRAY = new Color(127, 140, 141);
    private final Color BG = new Color(244, 247, 250);

    private JLabel lblJudul, lblSubJudul, lblTotal, lblStatus, lblHasil;
    private JTextField txtNama, txtBerat, txtTinggi;
    private JComboBox<String> cmbGender;
    private JButton btnHitung, btnReset, btnHapus;
    private JTextArea txtRiwayat;

    private JPanel panelHeader, panelForm, panelRiwayat, panelTengah, panelTombol, panelBawah;
    private JScrollPane scrollRiwayat;

    private int jumlahHitung = 0;

    public BMICalculator() {
        super("BMI Check | Kalkulator Indeks Massa Tubuh");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.setBackground(BG);

        panelHeader = new JPanel(new BorderLayout());
        panelHeader.setBackground(TEAL);
        panelHeader.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        lblJudul = new JLabel("BMI Check");
        lblJudul.setFont(new Font("Arial", Font.BOLD, 24));
        lblJudul.setForeground(Color.WHITE);
        lblSubJudul = new JLabel("Cek indeks massa tubuhmu dengan mudah");
        lblSubJudul.setForeground(Color.WHITE);

        JPanel panelJudul = new JPanel(new GridLayout(2, 1));
        panelJudul.setOpaque(false);
        panelJudul.add(lblJudul);
        panelJudul.add(lblSubJudul);

        lblTotal = new JLabel("Total hitung: 0");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 13));
        lblTotal.setForeground(Color.WHITE);

        panelHeader.add(panelJudul, BorderLayout.WEST);
        panelHeader.add(lblTotal, BorderLayout.EAST);

        panelForm = new JPanel(new GridLayout(5, 2, 10, 12));
        panelForm.setBackground(Color.WHITE);
        panelForm.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 225, 230)),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)));

        txtNama = new JTextField();
        cmbGender = new JComboBox<String>(new String[] { "Laki-laki", "Perempuan" });
        txtBerat = new JTextField();
        txtTinggi = new JTextField();
        lblHasil = new JLabel("-", SwingConstants.CENTER);
        lblHasil.setFont(new Font("Arial", Font.BOLD, 15));

        panelForm.add(new JLabel("Nama"));
        panelForm.add(txtNama);
        panelForm.add(new JLabel("Jenis Kelamin"));
        panelForm.add(cmbGender);
        panelForm.add(new JLabel("Berat Badan (kg)"));
        panelForm.add(txtBerat);
        panelForm.add(new JLabel("Tinggi Badan (cm)"));
        panelForm.add(txtTinggi);
        panelForm.add(new JLabel("Hasil BMI"));
        panelForm.add(lblHasil);

        txtRiwayat = new JTextArea(5, 20);
        txtRiwayat.setEditable(false);
        txtRiwayat.setFont(new Font("Monospaced", Font.PLAIN, 12));
        scrollRiwayat = new JScrollPane(txtRiwayat);

        JLabel lblRiwayat = new JLabel("Riwayat Perhitungan");
        lblRiwayat.setFont(new Font("Arial", Font.BOLD, 14));

        panelRiwayat = new JPanel(new BorderLayout(0, 5));
        panelRiwayat.setOpaque(false);
        panelRiwayat.add(lblRiwayat, BorderLayout.NORTH);
        panelRiwayat.add(scrollRiwayat, BorderLayout.CENTER);

        panelTengah = new JPanel(new BorderLayout(0, 15));
        panelTengah.setBackground(BG);
        panelTengah.setBorder(BorderFactory.createEmptyBorder(15, 20, 5, 20));
        panelTengah.add(panelForm, BorderLayout.NORTH);
        panelTengah.add(panelRiwayat, BorderLayout.CENTER);

        btnHitung = buatTombol("Hitung BMI", TEAL);
        btnReset = buatTombol("Reset", ORANGE);
        btnHapus = buatTombol("Hapus Riwayat", GRAY);

        panelTombol = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 8));
        panelTombol.setBackground(BG);
        panelTombol.add(btnHitung);
        panelTombol.add(btnReset);
        panelTombol.add(btnHapus);

        lblStatus = new JLabel("Siap.", SwingConstants.CENTER);
        lblStatus.setForeground(Color.GRAY);
        lblStatus.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));

        panelBawah = new JPanel(new BorderLayout());
        panelBawah.setBackground(BG);
        panelBawah.add(panelTombol, BorderLayout.CENTER);
        panelBawah.add(lblStatus, BorderLayout.SOUTH);

        btnHitung.addActionListener(this);
        btnReset.addActionListener(this);
        btnHapus.addActionListener(this);

        btnHitung.addMouseListener(this);
        btnReset.addMouseListener(this);
        btnHapus.addMouseListener(this);

        txtTinggi.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    hitungBMI();
                }
            }
        });

        cp.add(panelHeader, BorderLayout.NORTH);
        cp.add(panelTengah, BorderLayout.CENTER);
        cp.add(panelBawah, BorderLayout.SOUTH);

        setSize(480, 600);
        setLocationRelativeTo(null);
    }

    private JButton buatTombol(String teks, Color warna) {
        JButton b = new JButton(teks);
        b.setBackground(warna);
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Arial", Font.BOLD, 12));
        b.setOpaque(true);
        b.setFocusPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return b;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnHitung) {
            hitungBMI();
        } else if (e.getSource() == btnReset) {
            resetForm();
        } else if (e.getSource() == btnHapus) {
            txtRiwayat.setText("");
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == btnHitung) {
            lblStatus.setText("Klik untuk menghitung BMI");
        } else if (e.getSource() == btnReset) {
            lblStatus.setText("Klik untuk mengosongkan form");
        } else if (e.getSource() == btnHapus) {
            lblStatus.setText("Klik untuk menghapus riwayat");
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        lblStatus.setText("Siap.");
    }

    @Override public void mouseClicked(MouseEvent e) { }
    @Override public void mousePressed(MouseEvent e) { }
    @Override public void mouseReleased(MouseEvent e) { }

    private void hitungBMI() {
        try {
            String nama = txtNama.getText().trim();
            double berat = Double.parseDouble(txtBerat.getText().trim().replace(',', '.'));
            double tinggiM = Double.parseDouble(txtTinggi.getText().trim().replace(',', '.')) / 100;

            if (nama.isEmpty() || berat <= 0 || tinggiM <= 0) {
                JOptionPane.showMessageDialog(this, "Isi semua data dengan benar!",
                        "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            double bmi = berat / (tinggiM * tinggiM);
            String kategori;
            Color warna;
            if (bmi < 18.5) { kategori = "Kurus"; warna = new Color(41, 128, 185); }
            else if (bmi < 25) { kategori = "Normal"; warna = new Color(39, 174, 96); }
            else if (bmi < 30) { kategori = "Gemuk"; warna = ORANGE; }
            else { kategori = "Obesitas"; warna = new Color(192, 57, 43); }

            lblHasil.setText(String.format("%.1f (%s)", bmi, kategori));
            lblHasil.setForeground(warna);

            jumlahHitung++;
            lblTotal.setText("Total hitung: " + jumlahHitung);
            txtRiwayat.append(String.format("%d. %s (%s) - BMI %.1f - %s%n",
                    jumlahHitung, nama, cmbGender.getSelectedItem(), bmi, kategori));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Berat dan tinggi harus berupa angka!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetForm() {
        txtNama.setText("");
        txtBerat.setText("");
        txtTinggi.setText("");
        cmbGender.setSelectedIndex(0);
        lblHasil.setText("-");
        lblHasil.setForeground(Color.BLACK);
        txtNama.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BMICalculator().setVisible(true));
    }
}
