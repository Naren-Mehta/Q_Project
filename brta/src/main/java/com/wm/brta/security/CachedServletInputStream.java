package com.wm.brta.security;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;

public class CachedServletInputStream  extends ServletInputStream{
	private ByteArrayInputStream input;

	  public CachedServletInputStream() {
	      /* create a new input stream from the cached request body */
	    }
    public CachedServletInputStream(  ByteArrayOutputStream cachedBytes ) {
      /* create a new input stream from the cached request body */
      input = new ByteArrayInputStream(cachedBytes.toByteArray());
    }

    @Override
    public int read() throws IOException {
      return input.read();
    }

	@Override
	public boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isReady() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setReadListener(ReadListener listener) {
		// TODO Auto-generated method stub
		
	}
}
