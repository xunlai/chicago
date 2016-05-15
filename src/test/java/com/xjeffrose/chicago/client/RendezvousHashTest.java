package com.xjeffrose.chicago.client;

import com.google.common.collect.Lists;
import com.google.common.hash.Funnel;
import com.google.common.hash.Funnels;
import java.nio.charset.Charset;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.*;

public class RendezvousHashTest {
  private static final Funnel<CharSequence> strFunnel = Funnels.stringFunnel(Charset.defaultCharset());

  @Test
  public void get() throws Exception {
    List<String> nodes = Lists.newArrayList();
    for(int i = 0 ; i < 12; i ++) {
      nodes.add("node"+i);
    }
    RendezvousHash rendezvousHash1 = new RendezvousHash(strFunnel , nodes);
    RendezvousHash rendezvousHash2 = new RendezvousHash(strFunnel , nodes);

    int loopCount = 0;

    for (int i = 0; i < 10000; i++) {
      byte[] x = ("key" + i).getBytes();
      assertEquals(rendezvousHash1.get(x), rendezvousHash2.get(x));
      if (i == 9999) {
        i = 0;
        loopCount++;
      }

      if (loopCount == 2) {
        break;
      }
    }

    rendezvousHash1.remove("node3");
    rendezvousHash2.remove("node3");

    loopCount = 0;

    for (int i = 0; i < 10000; i++) {
      byte[] x = ("key" + i).getBytes();
      assertEquals(rendezvousHash1.get(x), rendezvousHash2.get(x));
      if (i == 9999) {
        i = 0;
        loopCount++;
      }

      if (loopCount == 2) {
        break;
      }
    }

    rendezvousHash1.add("node3");
    rendezvousHash2.add("node3");

    loopCount = 0;

    for (int i = 0; i < 10000; i++) {
      byte[] x = ("key" + i).getBytes();
      assertEquals(rendezvousHash1.get(x), rendezvousHash2.get(x));
      if (i == 9999) {
        i = 0;
        loopCount++;
      }

      if (loopCount == 2) {
        break;
      }
    }


  }

}