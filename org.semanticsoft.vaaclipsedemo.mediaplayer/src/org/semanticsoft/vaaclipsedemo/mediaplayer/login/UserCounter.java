package org.semanticsoft.vaaclipsedemo.mediaplayer.login;

import java.util.concurrent.atomic.AtomicInteger;

public class UserCounter
{
	private AtomicInteger value = new AtomicInteger(0);

	public int getValue()
	{
		return value.get();
	}

	public int increment()
	{
		int v;
		do
		{
			v = value.get();
		}
		while (!value.compareAndSet(v, v + 1));
		return v + 1;
	}
}
