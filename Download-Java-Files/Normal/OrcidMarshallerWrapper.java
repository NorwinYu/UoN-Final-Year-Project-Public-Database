package org.orcid.api.common.jaxb;

import java.io.File;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import javax.annotation.Resource;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.attachment.AttachmentMarshaller;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Result;
import javax.xml.validation.Schema;

import org.orcid.core.locale.LocaleManager;
import org.w3c.dom.Node;
import org.xml.sax.ContentHandler;

/**
 * 
 * @author Will Simpson
 * 
 */
public class OrcidMarshallerWrapper implements Marshaller {

    private Marshaller marshaller;
    
    @Resource
    private LocaleManager localeManager;

    public OrcidMarshallerWrapper(Marshaller marshaller) {
        this.marshaller = marshaller;
    }

    @Override
    public void marshal(Object jaxbElement, Result result) throws JAXBException {
        marshaller.marshal(jaxbElement, result);
    }

    @Override
    public void marshal(Object jaxbElement, OutputStream os) throws JAXBException {
        try {
            marshaller.marshal(jaxbElement, new FilterInvalidXmlCharsOutputStreamWriter(os, (String) marshaller.getProperty(JAXB_ENCODING)));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(localeManager.resolveMessage("apiError.output_stream_filter.exception"), e);
        }
    }

    @Override
    public void marshal(Object jaxbElement, File output) throws JAXBException {
        marshaller.marshal(jaxbElement, output);
    }

    @Override
    public void marshal(Object jaxbElement, Writer writer) throws JAXBException {
        marshaller.marshal(jaxbElement, writer);
    }

    @Override
    public void marshal(Object jaxbElement, ContentHandler handler) throws JAXBException {
        marshaller.marshal(jaxbElement, handler);
    }

    @Override
    public void marshal(Object jaxbElement, Node node) throws JAXBException {
        marshaller.marshal(jaxbElement, node);
    }

    @Override
    public void marshal(Object jaxbElement, XMLStreamWriter writer) throws JAXBException {
        marshaller.marshal(jaxbElement, writer);
    }

    @Override
    public void marshal(Object jaxbElement, XMLEventWriter writer) throws JAXBException {
        marshaller.marshal(jaxbElement, writer);
    }

    @Override
    public Node getNode(Object contentTree) throws JAXBException {
        return marshaller.getNode(contentTree);
    }

    @Override
    public void setProperty(String name, Object value) throws PropertyException {
        marshaller.setProperty(name, value);
    }

    public Object getProperty(String name) throws PropertyException {
        return marshaller.getProperty(name);
    }

    @Override
    public void setEventHandler(ValidationEventHandler handler) throws JAXBException {
        marshaller.setEventHandler(handler);
    }

    @Override
    public ValidationEventHandler getEventHandler() throws JAXBException {
        return marshaller.getEventHandler();
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void setAdapter(XmlAdapter adapter) {
        marshaller.setAdapter(adapter);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public <A extends XmlAdapter> void setAdapter(Class<A> type, A adapter) {
        marshaller.setAdapter(type, adapter);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public <A extends XmlAdapter> A getAdapter(Class<A> type) {
        return marshaller.getAdapter(type);
    }

    @Override
    public void setAttachmentMarshaller(AttachmentMarshaller am) {
        marshaller.setAttachmentMarshaller(am);
    }

    @Override
    public AttachmentMarshaller getAttachmentMarshaller() {
        return marshaller.getAttachmentMarshaller();
    }

    @Override
    public void setSchema(Schema schema) {
        marshaller.setSchema(schema);
    }

    @Override
    public Schema getSchema() {
        return marshaller.getSchema();
    }

    @Override
    public void setListener(Listener listener) {
        marshaller.setListener(listener);
    }

    @Override
    public Listener getListener() {
        return marshaller.getListener();
    }

}
