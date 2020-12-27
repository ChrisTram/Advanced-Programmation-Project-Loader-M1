package fr.unice.miage.game.sprite.layout;

public interface StayInLimitListener {
    StayInLimitListener EMPTY_LISTENER = new StayInLimitListener() {
        @Override
        public void touchTop(double overflow) {}

        @Override
        public void touchBottom(double overflow) {}

        @Override
        public void touchLeft(double overflow) {}

        @Override
        public void touchRight(double overflow) {}
    };

    void touchTop(double overflow);
    void touchBottom(double overflow);
    void touchLeft(double overflow);
    void touchRight(double overflow);
}
