package com.andy.ext;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.jfinal.plugin.IPlugin;

public class GuicePlugin implements IPlugin{

	Injector injector;
	@Override
	public boolean start() {
		Injector injector = Guice.createInjector(new GuiceAnnoModule());
		GuiceUtil.init(injector);
		this.injector = injector;
		return true;
	}

	@Override
	public boolean stop() {
		this.injector = null;
		return true;
	}

}
