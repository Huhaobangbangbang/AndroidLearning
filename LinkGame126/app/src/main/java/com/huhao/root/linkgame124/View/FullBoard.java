package com.huhao.root.linkgame124.View;

import com.huhao.root.linkgame124.AlgorithmDesign.AbstractBoard;//困难模式
import com.huhao.root.linkgame124.AlgorithmDesign.GameConf;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huhao201613161003
 */
//平面布局
public class FullBoard extends AbstractBoard {
    @Override
    protected List<Piece> createPieces(GameConf config, Piece[][] pieces) {
        List<Piece> notNullPieces = new ArrayList<Piece>();
        for(int i = 1; i<pieces.length - 1; i++){
            for (int j=1;j<pieces[i].length-1;j++){
                Piece piece = new Piece(i, j);
                notNullPieces.add(piece);
            }
        }
        return notNullPieces;
    }
}
