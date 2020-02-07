package patterns.structural.adapter;

interface ISession {
   void login();
   void logout();
}

class NormalSession implements ISession {

   @Override
   public void login() {
      System.out.println("Normal login");
   }

   @Override
   public void logout() {
      System.out.println("Normal logout");
   }
}

class FacebookSession {
   public void loginWithFacebook() {
      System.out.println("Facebook login");
   }

   public void logoutWithFacebook() {
      System.out.println("Facebook logout");
   }
}

class FacebookSessionAdapter implements ISession {
   private FacebookSession facebookSession = new FacebookSession();

   @Override
   public void login() {
      this.facebookSession.loginWithFacebook();
   }

   @Override
   public void logout() {
      this.facebookSession.logoutWithFacebook();
   }
}

class AdapterDemo {
   public static void main(String[] args) {
      NormalSession normalSession = new NormalSession();
      normalSession.login();
      normalSession.logout();
      FacebookSessionAdapter facebookSessionAdapter = new FacebookSessionAdapter();
      facebookSessionAdapter.login();
      facebookSessionAdapter.logout();
   }
}