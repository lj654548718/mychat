package com.mychat.mqtest;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.StreamMessage;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.activemq.command.ActiveMQBytesMessage;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.activemq.command.ActiveMQStreamMessage;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.alibaba.fastjson.JSONObject;
import com.mychat.common.mq.config.RequestConfig;
import com.mychat.common.mq.imp.binary.MultiBinQueueRequest;
import com.mychat.common.mq.imp.binary.MultiBinQueueResponse;
import com.mychat.common.mq.imp.binary.MultiBinRequest;
import com.mychat.common.mq.imp.binary.MultiBinResponse;
import com.mychat.common.mq.imp.binary.MultiBinTopicRequest;
import com.mychat.common.mq.imp.binary.MultiBinTopicResponse;
import com.mychat.common.mq.imp.file.*;
import com.mychat.common.mq.imp.json.MultiJsonQueueRequest;
import com.mychat.common.mq.imp.json.MultiJsonQueueResponse;
import com.mychat.common.mq.imp.json.MultiJsonRequest;
import com.mychat.common.mq.imp.json.MultiJsonResponse;
import com.mychat.common.mq.imp.json.MultiJsonTopicRequest;
import com.mychat.common.mq.imp.json.MultiJsonTopicResponse;
import com.mychat.common.mq.imp.string.MultiStringQueueRequest;
import com.mychat.common.mq.imp.string.MultiStringQueueResponse;
import com.mychat.common.mq.imp.string.MultiStringRequest;
import com.mychat.common.mq.imp.string.MultiStringResponse;
import com.mychat.common.mq.imp.string.MultiStringTopicRequest;
import com.mychat.common.mq.imp.string.MultiStringTopicResponse;

import static org.junit.Assert.*;
import static org.junit.Assume.*;

public class FileMqDemo extends BaseMqDemo {

	@Test
	public void testStart() throws JMSException {
		// normal
		testNormalRequest();
		testNormalResponse();

		// queue
		testQueueRequest();
		testQueueResponse();

		// topic
		testTopicRequest();
		testTopicResponse();
	}
	

	@Override
	protected void testNormalRequest() throws JMSException {
		// TODO Auto-generated method stub
		MultiFileRequest request = new MultiFileRequest(requestConfig);
		request.setData(file);
		request.send();
	}

	@Override
	protected void testQueueRequest() throws JMSException {
		MultiFileQueueRequest request = new MultiFileQueueRequest(requestConfig);
		request.setData(file);
		request.send();
	}

	@Override
	protected void testTopicRequest() throws JMSException {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(2222);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				MultiFileTopicRequest request = new MultiFileTopicRequest(requestConfig);
				request.setData(file);
				request.send();
			}
		}).start();
	}

	@Override
	protected void testNormalResponse() throws JMSException {
		// TODO Auto-generated method stub
		MultiFileResponse response = new MultiFileResponse(responseConfig);
		File data = response.receive().getData();
		System.out.println(data.getName()+":"+data.length());
		assertEquals("pom.xml:4036", data.getName()+":"+data.length());
	}

	@Override
	protected void testQueueResponse() throws JMSException {
		MultiFileQueueResponse response = new MultiFileQueueResponse(responseConfig);
		File data = response.receive().getData();
		System.out.println(data.getName()+":"+data.length());
		assertEquals("pom.xml:4036", data.getName()+":"+data.length());
	}

	@Override
	protected void testTopicResponse() throws JMSException {
		MultiFileTopicResponse response = new MultiFileTopicResponse(responseConfig);
		File data = response.receive().getData();
		System.out.println(data.getName()+":"+data.length());
		assertEquals("pom.xml:4036", data.getName()+":"+data.length());
	}

}
