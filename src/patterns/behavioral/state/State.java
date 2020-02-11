package patterns.behavioral.state;

class State {
   public void on(LightSwitch lightSwitch) {

   }

   public void off(LightSwitch lightSwitch) {

   }
}

class OnState extends State {
   public OnState() {
      System.out.println("Light turned on");
   }

   @Override
   public void on(LightSwitch lightSwitch) {
      System.out.println("Light is already on");
   }

   @Override
   public void off(LightSwitch lightSwitch) {
      System.out.println("Switching light off...");
      lightSwitch.setState(new OffState());
   }
}

class OffState extends State {
   public OffState() {
      System.out.println("Light turned off");
   }

   @Override
   public void on(LightSwitch lightSwitch) {
      System.out.println("Switching light on...");
      lightSwitch.setState(new OnState());
   }

   @Override
   public void off(LightSwitch lightSwitch) {
      System.out.println("Light is already off");
   }
}

class LightSwitch {
   private State state;

   public LightSwitch() {
      state = new OffState();
   }

   void on() {
      state.on(this);
   }

   void off() {
      state.off(this);
   }

   public void setState(State state) {
      this.state = state;
   }
}

class DemoState {
   public static void main(String[] args) {
      LightSwitch lightSwitch = new LightSwitch();
      lightSwitch.on();
      lightSwitch.off();
      lightSwitch.off();
   }
}
