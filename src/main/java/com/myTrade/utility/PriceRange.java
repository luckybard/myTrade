package com.myTrade.utility;

import lombok.Data;

@Data
public class PriceRange {
     private int from;
     private int to;

     public PriceRange(int from, int to) {
          this.from = from;
          this.to = to;
     }
}