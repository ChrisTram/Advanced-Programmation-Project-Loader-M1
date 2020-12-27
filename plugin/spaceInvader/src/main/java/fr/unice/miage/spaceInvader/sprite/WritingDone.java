package fr.unice.miage.spaceInvader.sprite;

public interface WritingDone {
    WritingDone emptyInstance = new WritingDone() {
        @Override
        public void done(DelayWriting writing) {}
    };
    void done(DelayWriting writing);
}
