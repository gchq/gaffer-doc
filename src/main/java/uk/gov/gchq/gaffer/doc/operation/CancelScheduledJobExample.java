/*
 * Copyright 2019 Crown Copyright
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.gov.gchq.gaffer.doc.operation;

import uk.gov.gchq.gaffer.jobtracker.Job;
import uk.gov.gchq.gaffer.jobtracker.JobDetail;
import uk.gov.gchq.gaffer.jobtracker.Repeat;
import uk.gov.gchq.gaffer.operation.OperationChain;
import uk.gov.gchq.gaffer.operation.OperationException;
import uk.gov.gchq.gaffer.operation.impl.get.GetAllElements;
import uk.gov.gchq.gaffer.operation.impl.job.CancelScheduledJob;
import uk.gov.gchq.gaffer.operation.impl.job.GetJobDetails;
import uk.gov.gchq.gaffer.user.User;

import java.util.concurrent.TimeUnit;

public class CancelScheduledJobExample extends OperationExample {

    private String jobId;

    public CancelScheduledJobExample() {
        super(CancelScheduledJob.class, "Cancels a scheduled job specified " +
                "by the job id");
    }

    public static void main(final String[] args) {
        new CancelScheduledJobExample().runAndPrint();
    }

    @Override
    protected void runExamples() {
        jobId = createJobId();
        scheduledJobBeforeBeingCancelled();
        cancelScheduledJob();
        scheduledJobAfterBeingCancelled();
    }

    public JobDetail scheduledJobBeforeBeingCancelled() {
        // ---------------------------------------------------------
        final OperationChain<JobDetail> operationChain = new OperationChain.Builder()
                .first(new GetJobDetails.Builder().jobId(jobId).build())
                .build();
        // ---------------------------------------------------------

        return runExample(operationChain, null);
    }

    public JobDetail scheduledJobAfterBeingCancelled() {
        // ---------------------------------------------------------
        final OperationChain<JobDetail> operationChain = new OperationChain.Builder()
                .first(new GetJobDetails.Builder().jobId(jobId).build())
                .build();
        // ---------------------------------------------------------

        return runExample(operationChain, null);
    }

    public void cancelScheduledJob() {

        // ---------------------------------------------------------
        final OperationChain chain = new OperationChain.Builder()
                .first(new CancelScheduledJob.Builder()
                        .jobId(jobId)
                        .build())
                .build();
        // ---------------------------------------------------------

        runExampleNoResult(chain, null);
    }

    private String createJobId() {
        // schedule a job

        final Job job = new Job(new Repeat(1L, 1L, TimeUnit.MINUTES),
                new OperationChain.Builder().first(new GetAllElements()).build());

        final JobDetail returnedJobDetail;
        try {
            returnedJobDetail = getGraph().executeJob(job, new User("user01"));
        } catch (final OperationException e) {
            throw new RuntimeException(e);
        }

        // get the job id
        return returnedJobDetail.getJobId();
    }


}
