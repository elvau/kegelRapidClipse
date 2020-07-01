
package com.company.kegelapp.storage;

import one.microstream.storage.configuration.Configuration;
import one.microstream.storage.types.EmbeddedStorageManager;


public class DB
{
	public static DataRoot               root           = new DataRoot();
	public static EmbeddedStorageManager storageManager = Configuration.Default()
		.setBaseDirectory("/opt/wildfly/data/storage")
		.setChannelCount(2)
		.setBackupDirectory("/opt/wildfly/data/backup")
		.createEmbeddedStorageFoundation()
		.createEmbeddedStorageManager(DB.root)
		.start();
}
