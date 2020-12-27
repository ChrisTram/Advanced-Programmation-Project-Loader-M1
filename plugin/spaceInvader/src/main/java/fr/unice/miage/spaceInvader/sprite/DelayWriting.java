package fr.unice.miage.spaceInvader.sprite;

import fr.unice.miage.game.GameBoard;
import fr.unice.miage.game.sprite.FakeDecorator;
import fr.unice.miage.spaceInvader.sprite.TerminalElement;
import fr.unice.miage.spaceInvader.sprite.WritingDone;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

import java.util.Iterator;

public class DelayWriting extends FakeDecorator<TerminalElement> {
    private double pendingTime = 0;
    private double currentWait;
    private double durationEachChar;
    private char nextChar = 0;
    private Iterator<Integer> pendingToWrite;
    private Text currentText = null;
    private WritingDone writingDone = WritingDone.emptyInstance;
    private char latest = 0;

    public DelayWriting(TerminalElement decorated, double durationEachChar, Iterator<Integer> pendingToWrite) {
        super(decorated);
        this.durationEachChar = durationEachChar;
        currentWait = 0;
        setPendingToWrite(pendingToWrite);
    }

    public void setPendingToWrite(Iterator<Integer> pendingToWrite) {
        this.pendingToWrite = pendingToWrite;
        if(pendingToWrite.hasNext())
            nextChar = (char)(int)pendingToWrite.next();
    }

    public void setDurationEachChar(double durationEachChar) {
        this.durationEachChar = durationEachChar;
    }

    public WritingDone getWritingDone() {
        return writingDone;
    }

    public void setWritingDone(WritingDone writingDone) {
        this.writingDone = writingDone;
    }

    @Override
    public void update(double time, GameBoard b) {
        pendingTime += time;
        while (nextChar != 0 && pendingTime >= durationEachChar) {
            pendingTime -= durationEachChar;
            if (currentText == null) {
                currentText = new Text();
                decorated.addLine(currentText);
            }
            if (nextChar == '\n')
                decorated.addLine(currentText = new Text());
            else {
                if(isOverflow())
                    decorated.addLine(currentText = new Text());
                currentText.setText(currentText.getText()+nextChar);
            }
            if(pendingToWrite.hasNext()) {
                nextChar = (char) (int) pendingToWrite.next();

                if(latest != nextChar && (Character.isAlphabetic(nextChar) || Character.isDigit(nextChar)))
                    currentWait = durationEachChar;
                if(latest != nextChar && nextChar == ' ')
                    currentWait = durationEachChar * 8/7;
                else
                    currentWait = 0;
                latest = nextChar;
            }
            else {
                nextChar = 0;
                currentText = null;
                writingDone.done(this);
            }
        }
        super.update(time, b);
    }
    private boolean isOverflow() {
        return decorated.isOverflow(currentText);
    }
}
