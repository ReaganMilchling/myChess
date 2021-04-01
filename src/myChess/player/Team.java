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

        @Override
        public Player choosePlayer(Player.WhitePlayer white, Player.BlackPlayer black) {
            return white;
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

        @Override
        public Player choosePlayer(Player.WhitePlayer white, Player.BlackPlayer black) {
            return black;
        }
    };

    public abstract boolean isWhite();
    public abstract boolean isBlack();
    public abstract int getOffset();
    public abstract Player choosePlayer(Player.WhitePlayer white, Player.BlackPlayer black);

}
