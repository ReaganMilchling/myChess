package myChess.player;

public enum Team {
    WHITE() {
        @Override
        public boolean isWhite() {
            return true;
        }

        @Override
        public boolean isBlack() {
            return false;
        }

        @Override
        public int getOffset() {
            return -1;
        }
    },
    BLACK() {
        @Override
        public boolean isWhite() {
            return false;
        }

        @Override
        public boolean isBlack() {
            return true;
        }

        @Override
        public int getOffset() {
            return 1;
        }
    };

    public abstract boolean isWhite();
    public abstract boolean isBlack();
    public abstract int getOffset();

}
