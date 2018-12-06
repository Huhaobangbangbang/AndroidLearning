package com.huhao.root.linkgame124.View;

import com.huhao.root.linkgame124.AlgorithmDesign.AbstractBoard;//困难模式
import com.huhao.root.linkgame124.AlgorithmDesign.GameConf;

import java.util.ArrayList;
import java.util.List;

/**
 * 水平设置
 */
public class HorizontalBoard extends AbstractBoard {
    @Override
    protected List<Piece> createPieces(GameConf config, Piece[][] pieces) {
        List<Piece> notNullPieces = new ArrayList<Piece>();
        for (int i=0;i<pieces.length;i++){
            for(int j=0;j<pieces[i].length;j++){
                if (j % 2 == 0){
                    Piece piece = new Piece(i, j);
                    notNullPieces.add(piece);
                }
            }
        }
        return notNullPieces;
    }
}
