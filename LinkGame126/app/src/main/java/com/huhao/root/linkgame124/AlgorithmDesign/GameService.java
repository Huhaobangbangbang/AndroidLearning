package com.huhao.root.linkgame124.AlgorithmDesign;

import com.huhao.root.linkgame124.View.Piece;

public interface GameService {
    void start();

    Piece[][] getPieces();

    boolean hasPieces();

    Piece findPiece(float touchX, float touchY);

    LinkInfo link(Piece p1, Piece p2);
}
