package com.huhao.root.linkgame124.AlgorithmDesign;

import android.util.Log;

import com.huhao.root.linkgame124.ModeChoose.ImageUtil3;
import com.huhao.root.linkgame124.View.Piece;
import com.huhao.root.linkgame124.View.PieceImage;

import java.util.List;

public abstract class AbstractBoard3 {
    protected abstract List<Piece> createPieces(GameConf config, Piece[][] pieces);

    public Piece[][] create(GameConf config){
        Piece[][] pieces = new Piece[config.getXSize()][config.getYSize()];
        List<Piece> notNullPieces = createPieces(config, pieces);
        List<PieceImage> playImages = ImageUtil3.getPlayImages(config.getContext(), notNullPieces.size());
        int imageWidth = playImages.get(0).getImage().getWidth();
        int imageHeight = playImages.get(0).getImage().getHeight();
        Log.d("LinkGame", "imageWidth x imageHeight = " + imageWidth + "x" + imageHeight);
        GameConf.PIECE_WIDTH = imageWidth;
        GameConf.PIECE_HEIGHT = imageHeight;
        for(int i=0; i<notNullPieces.size();i++){
            Piece piece = notNullPieces.get(i);
            piece.setImage(playImages.get(i));
            piece.setBeginX(piece.getIndexX() * imageWidth + config.getBeginImageX());
            piece.setBeginY(piece.getIndexY() * imageHeight + config.getBeginImageY());
            pieces[piece.getIndexX()][piece.getIndexY()] = piece;
        }
        return pieces;
    }
}
