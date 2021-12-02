import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.util.HashMap;

public enum Input {
	Q(KeyEvent.VK_Q),
	W(KeyEvent.VK_W),
	E(KeyEvent.VK_E),
	R(KeyEvent.VK_R),
	T(KeyEvent.VK_T),
	Y(KeyEvent.VK_Y),
	U(KeyEvent.VK_U),
	I(KeyEvent.VK_I),
	O(KeyEvent.VK_O),
	P(KeyEvent.VK_P),
	A(KeyEvent.VK_A),
	S(KeyEvent.VK_S),
	D(KeyEvent.VK_D),
	F(KeyEvent.VK_F),
	G(KeyEvent.VK_G),
	H(KeyEvent.VK_H),
	J(KeyEvent.VK_J),
	K(KeyEvent.VK_K),
	L(KeyEvent.VK_L),
	Z(KeyEvent.VK_Z),
	X(KeyEvent.VK_X),
	C(KeyEvent.VK_C),
	V(KeyEvent.VK_V),
	B(KeyEvent.VK_B),
	N(KeyEvent.VK_N),
	M(KeyEvent.VK_M);
	
	private boolean pressed;
	private int key;
	
	private Input(int key) {
		this.pressed = false;
		this.key = key;
	}
	
	public boolean isPressed() {
		synchronized (Input.class) {
			return pressed;
		}
	}
	
	
	private static HashMap<Integer, Input> map = new HashMap<Integer,Input>();
	static {
		
		for (Input value : Input.values()) {
			Input.map.put(value.key, value);
		}
		
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent ke) {
                synchronized (Input.class) {
                        if (map.containsKey(ke.getKeyCode())) {
                        	Input value = map.get(ke.getKeyCode());
                            if      (ke.getID() == KeyEvent.KEY_PRESSED) value.pressed = true;
                            else if (ke.getID() == KeyEvent.KEY_RELEASED) value.pressed = false;
                        }
                    return false;
                }
            }
        });
    }
}
