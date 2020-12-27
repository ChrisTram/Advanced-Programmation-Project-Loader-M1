package fr.unice.miage.spaceInvader.game.state;

import fr.unice.miage.spaceInvader.gamemode.SpaceInvaderEngine;
import fr.unice.miage.spaceInvader.sprite.DelayWriting;
import fr.unice.miage.spaceInvader.sprite.TerminalElement;
import fr.unice.miage.spaceInvader.sprite.WritingDone;

import java.util.Collections;

public class StoryTellingPhase implements GameState, WritingDone {
    public final String KStrIntroduction = ""+
            "Cela faisait maintenant 2 jours que le drame s'etait produit. Johnny, bien decide a faire payer les coupables, "+
            "acheta un vaisseau a un vieil ami. C'etait l'heure de la revanche.";

    public final String KStrLevel2 = ""+
            "4 jours depuis le drame. Johnny avancait peniblement au milieu des hordes ennemies, en cette douce matinee d'hiver,"+
            " il entendit au loin les tirs raisonner au fin fond du vide de l'espace. Mais Johnny etait deja pret. ";

    public final String KStrLevel3 = ""+
            "8 jours depuis le drame. Johnny recuperait sur Pandore, planete tellurique riche en oxygene de la voie lactee de la Bulle Savonneuse, "+
            "un droide du nom de C6R4. Meme si il n'était qu'une IA, ils avaient la meme philosophie de vie suite au drame qui a bouleverse la vie "+
            "dans l'univers il y avait maintenant 8 jours.";
    public final String KStrLevel4 = ""+
            "10 jours depuis le drame. Johnny commencait a s'interroger sur le but de sa quete, y aura - t - il un jour une fin a cette guerre ? "+
            "Pourrons-nous un jour nous entendre à nouveau ? Ces questions rongeaient Johnny au plus profond de son etre..";
    public final String KStrLevel5 = "" +
            "12 jours depuis le drame. C'est fini, la musique de la marche imperiale se fait entendre du fond de l'etoile robotisee dans laquelle Johnny est enferme" +
            "Lorsque tout espoir disparut aux yeux de Johnny, C6R4 surgit et le libéra. Ils dérobèrent un X-Zwim dans le hangar de l'empire. " +
            "C'etait l'heure du dernier combat. Ils allaient payer pour avoir aime Star Wars 7.";

    private GameStateDone stateDone = GameStateDone.empty;
    private DelayWriting delayWriting = null;
    private final SpaceInvaderEngine engine;
    private GameState nextState;
    private GameState finish;

    public StoryTellingPhase(SpaceInvaderEngine engine, GameState nextState, GameState finish) {
        this.engine = engine;
        this.nextState = nextState;
        this.finish = finish;
    }

    public DelayWriting getDelayWriting() {
        return delayWriting;
    }

    @Override
    public void initialize() {
        delayWriting = new DelayWriting(engine.getTerminalElement(),0.06, Collections.emptyIterator());
        delayWriting.setWritingDone(this);
        engine.getTerminalElement().clear();
        engine.getCurrent().addSprite(delayWriting);
        switch (engine.getPhaseCounter()) {
            case 0:
                delayWriting.setPendingToWrite(KStrIntroduction.chars().iterator());
                break;
            case 1:
                delayWriting.setPendingToWrite(KStrLevel2.chars().iterator());
                break;
            case 2:
                delayWriting.setPendingToWrite(KStrLevel3.chars().iterator());
                break;
            case 3:
                delayWriting.setPendingToWrite(KStrLevel4.chars().iterator());
                break;
            case 4:
                delayWriting.setPendingToWrite(KStrLevel5.chars().iterator());
                break;
            default:
                stateDone.done(this);
                uninitialized();
                finish.initialize();
                break;
        }
    }

    @Override
    public void setStateDone(GameStateDone stateDone) {
        this.stateDone = stateDone;
    }

    @Override
    public void uninitialized() {
        engine.getCurrent().removeSprite(delayWriting);
    }

    @Override
    public void done(DelayWriting writing) {
        stateDone.done(this);
        uninitialized();
        switch (engine.getPhaseCounter()) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
                nextState.initialize();
                break;
            default:
                finish.initialize();
                break;
        }
    }
}
