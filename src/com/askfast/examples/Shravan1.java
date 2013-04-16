
package com.askfast.examples;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.askfast.config.DialogSettings;
import com.askfast.model.AskFast;


@Path( "/shravan1" )
public class Shravan1 extends AskFast
{
    public Shravan1()
    {
        super( getUrl() );
    }

    private ArrayList<String> cSampleAnswers = new ArrayList<String>(
        Arrays.asList( "Yup", "Nope" ) );
    private ArrayList<String> cSampleResponses = new ArrayList<String>(
        Arrays.asList( "Thanks for making time!",
            "We will miss you!",
            "Something went wrong in this conversation.." ) );

    @GET
    @Produces( "text/plain" )
    public Response firstQuestion(
        @QueryParam( "preferred_medium" ) String preferred_medium,
        @QueryParam( "responder" ) String responder )
    {
        ask( "Are you coming to my bday party at Rotterdam?", null );
        addAnswer( cSampleAnswers.get( 0 ), getUrl() + "/questions/10" );
        addAnswer( cSampleAnswers.get( 1 ), getUrl() + "/questions/11" );
        addAnswer( "Appointment", getUrl() + "/questions/12" );
        return endDialog();
    }

    protected static String getUrl()
    {
        return DialogSettings.HOST + "/shravan1";
    }

    @Path( "/questions/{question_no}" )
    @GET
    @Produces( "text/plain" )
    @Consumes( "*/*" )
    public Response getQuestionText(
        @PathParam( "question_no" ) String question_no,
        @QueryParam( "preferred_medium" ) String prefered_mimeType )
    {
        if ( question_no.equals( "10" ) )
        {
            return say( cSampleResponses.get( 0 ) );
        }
        else if ( question_no.equals( "11" ) )
        {
            return say( cSampleResponses.get( 1 ) );
        }
        else if ( question_no.equals( "12" ) )
        {
            return say( "Transferring you to Appointment agent" );
        }
        else
        {
            return say( cSampleResponses.get( 2 ) );
        }
    }

    @Path( "/answers/{answer_no}" )
    @GET
    @Produces( "text/plain" )
    @Consumes( "*/*" )
    public Response getAnswerText( @PathParam( "answer_no" ) String answer_no,
        @QueryParam( "preferred_medium" ) String prefered_mimeType )
    {
        return getAnswerText( answer_no );
    }

    @Path( "/questions/{id}" )
    @POST
    @Produces( "application/json" )
    @Consumes( "*/*" )
    public Response answerQuestion( String answer_json,
        @PathParam( "id" ) String answerId,
        @QueryParam( "preferred_medium" ) String preferred_medium,
        @QueryParam( "responder" ) String responder )
    {
        if ( answerId.equals( "1" ) )
        {
            ask( "Are you coming to my bday party?", null );
            addAnswer( "Yup", getUrl() + "/questions/10" );
            addAnswer( "Nope", getUrl() + "/questions/11" );
        }
        else if ( answerId.equals( "10" ) )
        {
            say( cSampleResponses.get( 0 ) );
        }
        else if ( answerId.equals( "11" ) )
        {
            say( cSampleResponses.get( 1 ) );
        }
        else if ( answerId.equals( "12" ) )
        {
            return redirect( DialogSettings.HOST + "/questionanswer" );
        }
        else if ( answerId.equals( "3" ) )
        {
            say( cSampleResponses.get( 2 ) );
        }

        Logger log = Logger.getLogger( Shravan1.class.getName() );
        log.setLevel( Level.INFO );
        Response endDialog = endDialog();
        log.info( "ending dialog " + endDialog.getEntity().toString() );
        return endDialog;
    }
}
