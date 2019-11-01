import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.awt.Font;

public class Hamming {

	private JFrame frame;
	private JTextField receiveText;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Hamming window = new Hamming();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// calculating value of redundant bits
	static int[] calculation(int[] ar, int r) {
		for (int i = 0; i < r; i++) {
			int x = (int) Math.pow(2, i);
			for (int j = 1; j < ar.length; j++) {
				if (((j >> i) & 1) == 1) {
					if (x != j)
						ar[x] = ar[x] ^ ar[j];
				}
			}
			// System.out.println("r" + x + " = " + ar[x]);
		}

		return ar;
	}

	static ArrayList<Integer> checkHamming(int[] ar, int r) {
		ArrayList<Integer> list = new ArrayList<>();
		for (int i = 0; i < r; i++) {
			int x = (int) Math.pow(2, i);
			int n = 0;
			for (int j = 1; j < ar.length; j++) {
				if (((j >> i) & 1) == 1) {
					n = n ^ ar[j];
				}
			}
			list.add(n);
			// System.out.println("r" + x + " = " + ar[x]);
		}

		return list;
	}

	static int getErrorIdx(ArrayList<Integer> list) {
		int idx = 0;

		for (int i = 0; i < list.size(); i++) {
			idx += (int) Math.pow(2, i) * list.get(i);
		}

		return idx;
	}

	static int[] fixCode(int[] ar, int index) {
		if (index == 0)
			return ar;
		else {
			if (ar[index] == 1)
				ar[index] = 0;
			else
				ar[index] = 1;
			return ar;
		}
	}

	static int[] generateCode(String str, int M, int r) {
		int[] ar = new int[r + M + 1];
		int j = 0;
		for (int i = 1; i < ar.length; i++) {
			if ((Math.ceil(Math.log(i) / Math.log(2)) - Math.floor(Math.log(i) / Math.log(2))) == 0) {

				// if i == 2^n for n in (0, 1, 2, .....)
				// then ar[i]=0
				// codeword[i] = 0 ----
				// redundant bits are intialized
				// with value 0
				ar[i] = 0;
			} else {

				// codeword[i] = dataword[j]
				ar[i] = (int) (str.charAt(j) - '0');
				j++;
			}
		}
		return ar;
	}

	/**
	 * Create the application.
	 */
	public Hamming() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("해밍코드");
		frame.setBounds(100, 100, 600, 350);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(7, 1, 0, 0));

		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1);

		JLabel lblOriginData = new JLabel("데이터원본 : ");
		lblOriginData.setFont(new Font("맑은 고딕", Font.PLAIN, 16));

		JTextField originText = new JTextField();
		originText.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
		originText.setColumns(10);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup().addContainerGap()
						.addComponent(lblOriginData, GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(originText, GroupLayout.DEFAULT_SIZE, 467, Short.MAX_VALUE).addGap(11)));
		gl_panel_1.setVerticalGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup().addGap(11)
						.addGroup(gl_panel_1
								.createParallelGroup(Alignment.BASELINE).addComponent(lblOriginData,
										GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(originText))
						.addGap(7)));
		panel_1.setLayout(gl_panel_1);

		JPanel panel_2 = new JPanel();
		frame.getContentPane().add(panel_2);

		JLabel lblHammingCode = new JLabel("해밍코드 : ");
		lblHammingCode.setFont(new Font("맑은 고딕", Font.PLAIN, 16));

		JLabel lblHammingShow = new JLabel("");
		lblHammingShow.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
		lblHammingShow.setHorizontalAlignment(SwingConstants.CENTER);
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup().addContainerGap()
						.addComponent(lblHammingCode, GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(lblHammingShow, GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE)
						.addContainerGap()));
		gl_panel_2
				.setVerticalGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup().addGap(6)
								.addComponent(lblHammingCode, GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE).addGap(12))
						.addGroup(gl_panel_2
								.createSequentialGroup().addContainerGap().addComponent(lblHammingShow,
										GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGap(20)));
		panel_2.setLayout(gl_panel_2);

		JPanel panel_3 = new JPanel();
		frame.getContentPane().add(panel_3);

		JButton btnHammingCreate = new JButton("해밍코드 생성");
		btnHammingCreate.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
		btnHammingCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String str = originText.getText();
				int M = str.length();
				int r = 1;

				while (Math.pow(2, r) < (M + r + 1)) {
					// r is number of redundant bits
					r++;
				}
				int[] ar = generateCode(str, M, r);

				System.out.println("Generated hamming code ");
				ar = calculation(ar, r);
				String result = "[";
				int cnt = 0;
				for (int i = 1; i < ar.length; i++) {
					if ((int) (Math.pow(2, cnt)) == i) {
						if (i != ar.length - 1) {
							result += Integer.toString(ar[i]) + ", ";
							cnt += 1;
						} else {
							result += Integer.toString(ar[i]);
							cnt += 1;
						}
					} else {
						if (i != ar.length - 1) {
							result += "'" + Integer.toString(ar[i]) + "', ";
						} else
							result += "'" + Integer.toString(ar[i]) + "'";
					}
				}
				result += "]";
				lblHammingShow.setText(result);

			}
		});
		panel_3.add(btnHammingCreate);

		JPanel panel_4 = new JPanel();
		frame.getContentPane().add(panel_4);

		JLabel lblRecieveData = new JLabel("수신 데이터 : ");
		lblRecieveData.setFont(new Font("맑은 고딕", Font.PLAIN, 16));

		receiveText = new JTextField();
		receiveText.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
		receiveText.setColumns(10);
		GroupLayout gl_panel_4 = new GroupLayout(panel_4);
		gl_panel_4.setHorizontalGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_4.createSequentialGroup().addContainerGap()
						.addComponent(lblRecieveData, GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(receiveText, GroupLayout.DEFAULT_SIZE, 461, Short.MAX_VALUE).addGap(12)));
		gl_panel_4
				.setVerticalGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_4.createSequentialGroup().addGap(11)
								.addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblRecieveData, GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(receiveText))
								.addGap(15)));
		panel_4.setLayout(gl_panel_4);

		JPanel panel_5 = new JPanel();
		frame.getContentPane().add(panel_5);

		JButton btnDataCheck = new JButton("수신 데이터 CHECK");
		btnDataCheck.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
		panel_5.add(btnDataCheck);

		JPanel panel_6 = new JPanel();
		frame.getContentPane().add(panel_6);
		panel_6.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblProcess = new JLabel("");
		lblProcess.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
		panel_6.add(lblProcess);

		JPanel panel_7 = new JPanel();
		frame.getContentPane().add(panel_7);
		panel_7.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblResult = new JLabel("");
		lblResult.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
		panel_7.add(lblResult);

		btnDataCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String str = receiveText.getText();
				int[] ar = new int[str.length() + 1];
				for (int i = 0; i < str.length(); i++) {
					ar[i + 1] = str.charAt(i) - '0';
				}
				int r = 0;
				while (Math.pow(2, r) < str.length()) {
					r++;
				}
				/*
				 * for (int i = 1; i < str.length() + 1; i++) { System.out.println(ar[i]); }
				 */

				ArrayList<Integer> errorCode = checkHamming(ar, r);
				int idx = getErrorIdx(errorCode);

				if (idx == 0) {
					lblProcess.setText("에러없음..");
					lblResult.setText(str);
				} else {
					lblProcess.setText(idx + "번 위치에 에러 비트 발생... 수정합니다...");
					ar = fixCode(ar, idx);
					String result = "";
					for (int i = 1; i < str.length() + 1; i++) {
						result += Integer.toString(ar[i]);
					}
					lblResult.setText(result);
				}
			}
		});
	}
}
