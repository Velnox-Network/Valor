package dev.wacho.valor.utils;

import com.google.common.base.Preconditions;

import java.util.List;
import java.util.stream.Collectors;

public class ValorUtils {

    /**
     * Gets completions for command arguments based on user input.
     *
     * @param args  the arguments provided by the user
     * @param input the list of possible completions
     * @return a list of completions based on user input
     */
    public static List<String> getCompletions(String[] args, List<String> input) {
        return getCompletions(args, input, 80);
    }

    /**
     * Gets completions for command arguments based on user input with a specified limit.
     *
     * @param args  the arguments provided by the user
     * @param input the list of possible completions
     * @param limit the maximum number of completions to be returned
     * @return a list of completions based on user input, limited by the specified limit
     */
    private static List<String> getCompletions(String[] args, List<String> input, int limit) {
        Preconditions.checkNotNull(args, "Arguments cannot be null");
        Preconditions.checkArgument(args.length != 0, "Arguments must not be empty");
        String argument = args[args.length - 1];

        return input.stream()
                .filter(string -> string.regionMatches(true, 0, argument, 0, argument.length()))
                .limit(limit)
                .collect(Collectors.toList());
    }
}
